# Building Management microservice
![workflow status](https://github.com/smartoperatingblock/building-management-microservice/actions/workflows/build-and-deploy.yml/badge.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This is the repository of the Building Management microservice of the Smart Operating Block project.

## Usage
You need to specify the following environment variable:
- `AZURE_CLIENT_ID`: ID of an Azure AD application
- `AZURE_TENANT_ID`: ID of the application's Azure AD tenant
- `AZURE_CLIENT_SECRET`: the application's client secrets
- `AZURE_DT_ENDPOINT`: the Azure Digital Twins instance endpoint
- `MONGODB_CONNECTION_STRING`: the mongodb's connection string
