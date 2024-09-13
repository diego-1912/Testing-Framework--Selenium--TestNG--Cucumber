# Test Automation Project

## Overview
This project is a test automation framework for the SauceDemo website (https://www.saucedemo.com). It uses Selenium WebDriver with Java, TestNG, and follows the Page Object Model (POM) design pattern. The framework is configured to run tests on multiple browsers and includes logging functionality using Log4j2.

## Table of Contents
1. [Project Structure](#project-structure)
2. [Setup](#setup)
3. [Configuration](#configuration)
4. [Running Tests](#running-tests)
5. [Jenkins Pipelines](#Jenkins-Pipeline)


## Project Structure
- `src/main/java/pages/`: Contains page object classes (BaseClass, LoginPage, DashboardPage)
- `src/test/java/my/project/Test/`: Contains test classes (LoginTest, DashboardTest)
- `src/test/resources/`: Contains configuration files (log4j2.xml, test-config.properties)
- `TestNG.xml`: TestNG suite configuration file
- `src/test/java/DriverFactory/`: Contains the driver configuration
- `src/test/java/TestListener/`: Contains the TestNG configuration


## Setup
1. Ensure you have Java JDK 17 or higher installed
2. Install Maven
3. Clone this repository
4. Run `mvn clean install` to download dependencies


## Configuration
- `test-config.properties`: Contains the base URL for the application
- `log4j2.xml`: Configures logging for different browsers
- `TestNG.xml`: Configures test suite, including browser parameter


## Running Tests
To run the tests, use the following command:  `mvn test` to run all the tests.


Replace `firefox` with `chrome` to run tests in Chrome.

## Jenkins Pipeline

The project includes a `Jenkinsfile` that defines the CI/CD pipeline with the following stages:

1. **Checkout**
2. **Build**
3. **Run Tests - Firefox**
4. **Run Tests - Chrome**
5. **Publish Test Results**
6. **Publish Extent Report**

## Jenkins Setup

1. Ensure Jenkins has JDK 17, Maven 3.8.4, TestNG Plugin, and HTML Publisher Plugin.
2. Create a new Pipeline job.
3. Set "Pipeline script from SCM" as the pipeline definition.
4. Specify your SCM (e.g., Git) and repository URL.
5. Set the script path to `Jenkinsfile`.

## Environment Variables

The `Jenkinsfile` sets these environment variables:

- `FIREFOX_BINARY`
- `CHROME_BINARY`
- `GECKODRIVER_PATH`
- `CHROMEDRIVER_PATH`

Ensure these paths are correct for your Jenkins agent.

## Reporting

- TestNG results are published using the TestNG plugin in Jenkins.
- Extent Reports are generated in the `test-output` directory.

## Logs

Test execution logs are archived in the `logs` directory.

## Troubleshooting

For Jenkins pipeline issues:

- Check for required tools and plugins on the Jenkins agent.
- Verify environment variable paths in the `Jenkinsfile`.
- Ensure proper permissions for test execution and report creation.

For local test issues, verify your Java, Maven, and WebDriver setups.