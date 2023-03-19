pipeline {
    agent any
    stages {
        stage('Checkout Codebase') {
            steps {
                checkout scm
                sh 'ls -l' // Vérifie que le code source est bien récupéré
            }
        }
        stage('Build and Test') {
            steps {
                def mvnHome = tool name: '', type: 'maven'
                sh "${mvnHome}/bin/mvn clean test"
                //sh './mvnw clean test' // Compile et exécute les tests unitaires
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' // Publie les résultats des tests
                }
            }
        }
    }
}
