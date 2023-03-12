# Building Management microservice
![workflow status](https://github.com/smartoperatingblock/building-management-microservice/actions/workflows/build-and-deploy.yml/badge.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Version](https://img.shields.io/github/v/release/smartoperatingblock/building-management-microservice?style=plastic)

[![codecov](https://codecov.io/gh/SmartOperatingBlock/building-management-microservice/branch/main/graph/badge.svg?token=7GL0gAUkQp)](https://codecov.io/gh/SmartOperatingBlock/building-management-microservice)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SmartOperatingBlock_building-management-microservice&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=SmartOperatingBlock_building-management-microservice)

This is the repository of the Building Management microservice of the Smart Operating Block project.

## Usage
You need to specify the following environment variable:
- `AZURE_CLIENT_ID`: ID of an Azure AD application
- `AZURE_TENANT_ID`: ID of the application's Azure AD tenant
- `AZURE_CLIENT_SECRET`: the application's client secrets
- `AZURE_DT_ENDPOINT`: the Azure Digital Twins instance endpoint
- `MONGODB_CONNECTION_STRING`: the mongodb's connection string
- `BOOTSTRAP_SERVER_URL`: the kafka connection endpoint
- `SCHEMA_REGISTRY_URL`: the schema registry url

If you want to run it via docker container:
1. Provide a `.env` file with all the environment variable described above
2. Run the container with the command:
   ```bash
    docker run ghcr.io/smartoperatingblock/building-management-microservice:latest
    ```
   1. If you want to try the REST-API from the external you need to provide a port mapping to port 3000.
   2. If you want to pass an environment file whose name is different from `.env` use the `--env-file <name>` parameter.

## Documentation
- Check out the website [here](https://smartoperatingblock.github.io/building-management-microservice/)
- Direct link to the *REST-API* documentation [here](https://smartoperatingblock.github.io/building-management-microservice/documentation/openapi-doc/)
- Direct link to the *Code* documentation [here](https://smartoperatingblock.github.io/building-management-microservice/documentation/code-doc/)
