pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                // Cloner le code source depuis votre système de gestion de version (par exemple Git)
                checkout scm

                // Installer Maven
                sh "mvn -version || curl -sSL https://get.mvnvm.org/ | bash"

                // Compiler le code source et exécuter les tests unitaires
                sh "mvn clean test"
            }
        }
    }
}
