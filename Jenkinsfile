pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your SCM (e.g. Git)
                checkout scm
            }
        }
        
        stage('Install Java and Maven') {
            steps {
                withCredentials([string(credentialsId: 'sudo-password', variable: 'sudoPassword')]) {
                    sh '''
                        echo $sudoPassword | sudo -S apt-get update
                        echo $sudoPassword | sudo -S apt-get install -y openjdk-11-jdk maven
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven and run the tests
                sh 'mvn -B -Dmaven.test.failure.ignore=true clean package'
            }
        }

        stage('Test') {
            steps {
                // Run the unit tests using the JVM
                sh 'java -jar -Dspring.profiles.active=test target/my-spring-boot-app.jar'
            }
        }
    }
}
