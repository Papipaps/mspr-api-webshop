pipeline {
  agent any
  
  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], 
                 userRemoteConfigs: [[url: 'https://github.com/Papipaps/mspr-api-webshop.git']]])
      }
    }
    
    stage('Maven Build') {
      environment {
        SECRET_FILE = credentials('secret-file-uuid')
      }
      steps {
        sh 'mvn clean test -DpropertiesFile=${SECRET_FILE}'
      }
    }
    
    stage('Docker Build') {
      steps {
        sh 'docker build -t webshop-service .'
      }
    }
    
    stage('Push Image') {
      steps {
        echo 'Pushing new image to Kube...'
      }
    }
    
  }
  
}
