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