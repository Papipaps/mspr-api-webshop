pipeline {
  agent any
  
  stages {
    //stage('Checkout') {
     // steps {
      //  checkout([$class: 'GitSCM', branches: [[name: '*/main']], 
       //          userRemoteConfigs: [[url: 'https://github.com/Papipaps/mspr-api-webshop.git']]])
     // }
   // }
    
    stage('Maven Build') {
      environment {
        SECRET_FILE = credentials('615fa613-cfa3-4c60-b2de-3102d91d776a')
      }
      steps {
        sh "mvn clean test -DpropertiesFile=${SECRET_FILE}"
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
