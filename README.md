
# SA Liabilities Sandpit Contract Tests

## Overview
This repository contains contract tests for the SA Liabilities Sandpit API, which retrieves liabilities details for users.
As this API is developed as a sampler project, the tests are run locally and not against any other environments such as QA. 
These tests ensure that the API behaves as expected in scenarios as per the JIRA ticket  [DI-198]("https://jira.tools.tax.service.gov.uk/browse/DI-198") and [DI-181]("https://jira.tools.tax.service.gov.uk/browse/DI-181").


## Pre-requisites
To run tests locally
1. Start all auth services 
2. Start the API service: [sa-liabilities-sandpit-api](https://github.com/hmrc/sa-liabilities-sandpit-api)
3. Start the Stubs: [sa-liabilities-sandpit-stubs](https://github.com/hmrc/sa-liabilities-sandpit-stubs)

### Running tests
 
* To run all tests:

```bash
sbt -Denv=local test 
```
OR

Use `./run-tests.sh`
* To run a specific test:

```bash
sbt -Denv=local 'testOnly features.{feature name here}'
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
