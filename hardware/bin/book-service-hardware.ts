#!/usr/bin/env node
import { App, Environment } from "aws-cdk-lib";
import "source-map-support/register";
import { BookServiceHardwareStack } from "../lib";

const env: Environment = {
  account: process.env.CDK_DEFAULT_ACCOUNT,
  region: process.env.CDK_DEFAULT_REGION,
};

const tags: { [key: string]: string } = {
  description: "CDK stack for the book service",
  environment: process.env.ENVIRONMENT || "unknown-environment",
  owner: "stephen@baremetal.help",
  repo: "https://github.com/baremetalhelp/baremetal-typical-application",
};

const applicationName = "book-service";
const applicationPort = 8080;

const app = new App();

new BookServiceHardwareStack(app, "BookServiceHardware", {
  env,
  tags,
  applicationName,
  applicationPort,
  healthCheckPath: "/actuator/health",
});
