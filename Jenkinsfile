pipeline {
    agent any

    stages {
        stage('Clone repository') {
            steps {
                git 'https://github.com/Papipaps/mspr-api-webshop.git'
            }
        }

        stage('Download Console Launcher') {
            steps {
                sh 'wget https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-console-standalone/1.8.2/junit-platform-console-standalone-1.8.2.jar'
            }
        }

        stage('Execute tests') {
            steps {
                sh 'java -jar junit-platform-console-standalone-1.8.2.jar --classpath build/classes/java/main --scan-class-path'
            }
        }
    }
}
