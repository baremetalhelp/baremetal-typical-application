import { Duration, Stack } from "aws-cdk-lib";
import { Certificate } from "aws-cdk-lib/aws-certificatemanager";
import { IVpc } from "aws-cdk-lib/aws-ec2";
import { IRepository } from "aws-cdk-lib/aws-ecr";
import {
  AwsLogDriver,
  ContainerImage,
  FargateService,
  FargateTaskDefinition,
  ICluster,
  Protocol,
} from "aws-cdk-lib/aws-ecs";
import {
  ApplicationLoadBalancer,
  ApplicationProtocol,
  Protocol as LoadBalancerProtocol,
} from "aws-cdk-lib/aws-elasticloadbalancingv2";
import { ARecord, HostedZone, RecordTarget } from "aws-cdk-lib/aws-route53";
import { LoadBalancerTarget } from "aws-cdk-lib/aws-route53-targets";
import { Bucket } from "aws-cdk-lib/aws-s3";
import { Construct } from "constructs";
import { BareMetalCertificate } from ".";

export interface BareMetalEcsServiceProps {
  applicationName: string;
  applicationPort: number;
  cluster: ICluster;
  domainName: string;
  repository: IRepository;
  testing?: boolean;
  vpc: IVpc;
}

export class BareMetalEcsService extends Construct {
  readonly certificate: Certificate;

  constructor(parent: Stack, name: string, props: BareMetalEcsServiceProps) {
    super(parent, name);

    const {
      applicationName,
      applicationPort,
      cluster,
      domainName,
      repository,
      testing,
      vpc,
    } = props;

    const port = testing ? 80 : applicationPort;
    const taskDefinition = new FargateTaskDefinition(this, "task-definition");
    const logging = new AwsLogDriver({
      streamPrefix: `baremetal-${applicationName}`,
    });
    const image = testing
      ? ContainerImage.fromRegistry("amazon/amazon-ecs-sample")
      : ContainerImage.fromEcrRepository(repository);
    const container = taskDefinition.addContainer(applicationName, {
      image,
      logging,
    });
    container.addPortMappings({
      containerPort: port,
      hostPort: port,
      protocol: Protocol.TCP,
    });

    const service = new FargateService(this, "service", {
      serviceName: applicationName,
      cluster,
      taskDefinition,
    });

    const certificate = new BareMetalCertificate(parent, "certificate", {
      subDomainName: applicationName,
      domainName,
    });

    const loadBalancer = new ApplicationLoadBalancer(this, "load-balancer", {
      vpc,
      internetFacing: true,
    });

    const logsBucket = new Bucket(this, "logs-bucket");
    loadBalancer.logAccessLogs(logsBucket, applicationName);

    const listener = loadBalancer.addListener("public-listener", {
      protocol: ApplicationProtocol.HTTP,
      // certificates: [certificate.certificate],
      open: true,
    });
    listener.addTargets("listener-target", {
      protocol: ApplicationProtocol.HTTP,
      targets: [
        service.loadBalancerTarget({
          containerName: applicationName,
          containerPort: port,
        }),
      ],
      healthCheck: {
        protocol: LoadBalancerProtocol.HTTP,
        interval: Duration.seconds(60),
        path: testing ? "/" : "/health",
        timeout: Duration.seconds(5),
      },
    });

    const zone = HostedZone.fromLookup(this, "hostedzone", {
      domainName,
    });

    const alias = new ARecord(this, "alias", {
      zone,
      recordName: `${applicationName}.${domainName}`,
      target: RecordTarget.fromAlias(new LoadBalancerTarget(loadBalancer)),
    });
  }
}
