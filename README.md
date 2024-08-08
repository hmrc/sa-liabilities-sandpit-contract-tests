
# SA Liabilities Sandpit Contract Tests

## Overview
This repository contains contract tests for the SA Liabilities Sandpit API, which retrieves liabilities details for users.
As this API is developed as a sampler project, the tests are run locally and not against any environments. These tests ensures that the API behaves as expected in scenarios as per teh functional stories: 

## Pre-requisites
To run tests locally:
1. Check for active HMRC MDTP VPN
2. Local machine is setup as per the MDTP Developer setup manual

### Running tests
 
* To run a specific test:

```bash
./run-contract-local.sh 
```
* To run a specific test:

```bash
sbt -Denv=local 'testOnly features.{feature name here}'
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
