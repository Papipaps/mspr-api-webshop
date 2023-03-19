pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your SCM (e.g. Git)
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
                sh 'java -jar target/demo-0.0.1-SNAPSHOT.jar'
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                sh 'java -jar target/demo-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
