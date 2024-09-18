# Selenium-TestNG-Cucumber Framework

This project implements a robust testing framework using Cucumber, TestNG, Java, Log4j2, and Selenium WebDriver. It's designed to perform automated testing on web applications, with a specific focus on the SauceDemo website.

## Project Structure

```
my.project.Test/
├── hooks/
│   └── Hooks.java
├── pages/
│   ├── BaseClass.java
│   ├── DashboardPage.java
│   └── LoginPage.java
├── runner/
│   └── CucumberRunner.java
├── stepDefinitions/
│   ├── DashboardStepDefinitions.java
│   └── LoginStepDefinitions.java
├── utils/
│   ├── ConfigReader.java
│   ├── DriverFactory.java
│   └── LogDirectoryInitializer.java
├── resources/
│   ├── features/
│   │   ├── Dashboard.feature
│   │   └── Login.feature
│   ├── config.properties
│   ├── extent.properties
│   └── log4j2.xml
└── target/
    └── cucumber-reports/
        └── extent-report.html
```

## Main Classes

1. **Hooks.java**: Manages setup and teardown operations for test scenarios.
2. **BaseClass.java**: Provides common functionalities for page objects.
3. **DashboardPage.java** & **LoginPage.java**: Page object classes for respective pages.
4. **CucumberRunner.java**: TestNG runner class for Cucumber tests.
5. **DashboardStepDefinitions.java** & **LoginStepDefinitions.java**: Step definition classes for Cucumber scenarios.
6. **ConfigReader.java**: Reads configuration properties from `config.properties`.
7. **DriverFactory.java**: Manages WebDriver instances.
8. **LogDirectoryInitializer.java**: Sets up log directories for different browsers.

## Configuration Files

1. **config.properties**: Contains browser and base URL configurations.
2. **extent.properties**: Configuration for Extent Reports.
3. **log4j2.xml**: Log4j2 configuration for logging.

## Feature Files

1. **Dashboard.feature**: Cucumber scenarios for dashboard functionality.
2. **Login.feature**: Cucumber scenarios for login functionality.

## Reports Location

Test reports are generated in the `target/cucumber-reports/` directory:
- `extent-report.html`: Extent report for test execution results.

## How to Run the Framework

1. Ensure you have Java 17 and Maven installed on your system.

2. Clone the repository and navigate to the project root directory.

3. Update `config.properties` if you need to change the browser or base URL:
   ```
   browser=firefox
   baseUrl=https://www.saucedemo.com
   ```

4. Run tests using Maven:
   ```
   mvn clean test
   ```

5. To run tests with a specific browser:
   ```
   mvn clean test -Dbrowser=chrome
   ```

6. To run a specific feature file:
   ```
   mvn clean test -Dcucumber.options="src/test/resources/features/Login.feature"
   ```

7. View the generated Extent Report at `target/cucumber-reports/extent-report.html`.

## Logging

Logs are generated in the `logs/` directory, with separate subdirectories for each browser type (chrome, firefox, edge).

## Customization

- Add new feature files in `src/test/resources/features/`.
- Implement corresponding step definitions in `stepDefinitions/`.
- Create new page object classes in `pages/` for new pages or components.

## Notes

- The framework uses ThreadLocal for WebDriver management, supporting parallel execution.
- Extent Reports are integrated for enhanced reporting capabilities.
- Log4j2 is configured for comprehensive logging across different browsers.

For any issues or contributions, please create an issue or pull request in the project repository.

## Jenkins Pipeline Configuration

This project includes a Jenkins pipeline configuration for continuous integration and delivery. Below is an explanation of the Jenkins pipeline structure:

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
        jdk 'JDK 17'
    }
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select the browser to run tests')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                script {
                    def browsers = ['chrome', 'firefox', 'edge']
                    browsers.each { browser ->
                        stage("Run Tests on ${browser.capitalize()}") {
                            try {
                                sh "mvn test -Dbrowser=${browser} -Dcucumber.filter.tags=\"@${browser}\""
                            } catch (Exception e) {
                                currentBuild.result = 'FAILURE'
                                error "Tests failed on ${browser}"
                            }
                        }
                    }
                }
            }
        }
        stage('Generate Reports') {
            steps {
                script {
                    cucumber buildStatus: 'UNSTABLE',
                            fileIncludePattern: '**/cucumber.json',
                            jsonReportDirectory: 'target'
                    publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'target/cucumber-reports',
                            reportFiles: 'extent-report.html',
                            reportName: 'Extent HTML Report',
                            reportTitles: ''
                    ])
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace'
            deleteDir()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
    }
}
```

### Pipeline Breakdown:

1. **Agent and Tools**: The pipeline can run on any available agent and uses Maven 3.8.1 and JDK 17.

2. **Parameters**: Allows selection of the browser for test execution.

3. **Stages**:
   - **Checkout**: Checks out the source code from the repository.
   - **Build**: Compiles the project using Maven.
   - **Test**: Runs tests on multiple browsers (chrome, firefox, edge) in parallel.
   - **Generate Reports**: Generates and publishes Cucumber and Extent HTML reports.

4. **Post Actions**: Cleans up the workspace and provides status messages.

### Running the Pipeline:

1. Set up a Jenkins server with necessary plugins (Pipeline, Git, Maven Integration, HTML Publisher).
2. Create a new Pipeline job in Jenkins.
3. In the job configuration, under "Pipeline", select "Pipeline script from SCM".
4. Select Git as SCM, and provide your repository URL.
5. Specify the path to the Jenkinsfile in your repository.
6. Save the configuration and run the pipeline.

This pipeline ensures that your tests are run on multiple browsers, with proper reporting and error handling. It's designed to integrate smoothly with your existing test framework and provide comprehensive CI/CD capabilities.
# Selenium-TestNG-Cucumber-Framework
