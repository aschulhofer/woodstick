# Woodstick

Utils test project

[![Build Status](https://travis-ci.org/aschulhofer/woodstick.svg?branch=master)](https://travis-ci.org/aschulhofer/woodstick)

### Testing
- Run unit tests with: `mvn test`
- Run unit & integration tests with: `mvn integration-test`

### Install 
- Install: `mvn install`
    - runs unit & integration tests
- Install: `mvn install -DskipITs`
    - runs unit tests, skips integration tests
- Install: `mvn install -DskipTests`
    - skips unit & integration tests

### Reporting
- Create Site and JavaDoc: `mvn site`