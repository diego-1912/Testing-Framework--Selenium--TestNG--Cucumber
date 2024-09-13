pipeline {
    agent any

    tools {
        maven 'Maven 3.8.4'
        jdk 'JDK 17'
    }

    environment {
        FIREFOX_BINARY = '/usr/bin/firefox'
        CHROME_BINARY = '/usr/bin/google-chrome-stable'
        GECKODRIVER_PATH = '/usr/local/bin/geckodriver'
        CHROMEDRIVER_PATH = '/usr/local/bin/chromedriver'
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

        stage('Run Tests - Firefox') {
            steps {
                sh 'mvn test -Dbrowser=firefox -DbaseUrl=https://www.saucedemo.com/'
            }
        }

        stage('Run Tests - Chrome') {
            steps {
                sh 'mvn test -Dbrowser=chrome -DbaseUrl=https://www.saucedemo.com/'
            }
        }

        stage('Publish Test Results') {
            steps {
                // Use either 'testng' (if you have the plugin) or 'junit' for standard test result publishing
                junit '**/target/surefire-reports/testng-results.xml'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
            cleanWs()
        }
    }
}
