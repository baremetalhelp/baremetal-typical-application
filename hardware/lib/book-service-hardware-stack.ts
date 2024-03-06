import { Stack, StackProps } from "aws-cdk-lib";
import { CfnService } from "aws-cdk-lib/aws-apprunner";
import {
  Peer,
  Port,
  SecurityGroup,
  SubnetType,
  Vpc,
} from "aws-cdk-lib/aws-ec2";
import {
  ManagedPolicy,
  PolicyDocument,
  Role,
  ServicePrincipal,
} from "aws-cdk-lib/aws-iam";
import {
  AuroraCapacityUnit,
  DatabaseClusterEngine,
  ServerlessCluster,
} from "aws-cdk-lib/aws-rds";
import { Statement } from "cdk-iam-floyd";
import { Construct } from "constructs";

export interface BookServiceHardwareStackProps extends StackProps {
  applicationName: string;
  applicationPort: number;
  healthCheckPath?: string;
  vpcName?: string;
}

export class BookServiceHardwareStack extends Stack {
  constructor(
    scope: Construct,
    id: string,
    props: BookServiceHardwareStackProps
  ) {
    super(scope, id, props);

    const { account, region } = Stack.of(this);
    const { applicationName, applicationPort, healthCheckPath, vpcName } =
      props;

    const vpc = Vpc.fromLookup(this, "vpc", {
      vpcName: vpcName || "baremetal-vpc",
    });

    const dbSecurityGroup = new SecurityGroup(this, "db-security-group", {
      vpc: vpc,
      allowAllOutbound: true,
    });

    // TODO: restrict to security group
    //
    dbSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(applicationPort));

    const dbCluster = new ServerlessCluster(this, "db-cluster", {
      engine: DatabaseClusterEngine.AURORA_MYSQL,
      vpc,
      vpcSubnets: {
        subnetType: SubnetType.PUBLIC,
      },
      securityGroups: [dbSecurityGroup],
      enableDataApi: true,
      defaultDatabaseName: "books",
      scaling: {
        minCapacity: AuroraCapacityUnit.ACU_1,
        maxCapacity: AuroraCapacityUnit.ACU_16,
      },
    });

    const { roleArn } = new Role(this, "access_role", {
      assumedBy: new ServicePrincipal("build.apprunner.amazonaws.com"),
      managedPolicies: [
        ManagedPolicy.fromAwsManagedPolicyName(
          "service-role/AWSAppRunnerServicePolicyForECRAccess"
        ),
      ],
      inlinePolicies: {
        access_db_cluster: new PolicyDocument({
          statements: [
            new Statement.Rds()
              .allow()
              .allActions()
              .onCluster(dbCluster.clusterIdentifier),
          ],
        }),
      },
    });

    const appRunnerService = new CfnService(this, "service", {
      serviceName: applicationName,
      sourceConfiguration: {
        authenticationConfiguration: {
          accessRoleArn: roleArn,
        },
        autoDeploymentsEnabled: true,
        imageRepository: {
          imageIdentifier: `${account}.dkr.ecr.${region}.amazonaws.com/${applicationName}:latest`,
          imageRepositoryType: "ECR",
          imageConfiguration: {
            port: `${applicationPort}`,
            runtimeEnvironmentVariables: [
              { name: "DB_HOST", value: dbCluster.clusterEndpoint.hostname },
              { name: "DB_CREDENTIALS", value: dbCluster.secret?.secretArn },
            ],
          },
        },
      },
      healthCheckConfiguration: {
        protocol: "HTTP",
        path: healthCheckPath,
      },
    });

    // Custom domain does not currently work, but will when we figure it out.
    // It looks like the cert must contain the AWS domain and we can't mint the cert to match
    //
    // So creating a custom apex domain in the console works, but custom subdomain does not
    //
    //       const endpoint = subDomainName
    //       ? subDomainName + "." + domainName
    //       : domainName;
    //
    // const { hostedZone } = new BareMetalCertificate(this, "certificate", {
    //     domainName,
    //     subDomainName,
    // });

    // new ARecord(this, "alias", {
    //     recordName: endpoint,
    //     zone: hostedZone,
    //     target: RecordTarget.fromAlias(new AliasTargetInstance)
    // });
  }
}
