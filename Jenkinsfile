pipeline {
  agent any
  tools {
        maven 'maven' 
    }
  
  stages {
    //stage('Checkout') {
     // steps {
      //  checkout([$class: 'GitSCM', branches: [[name: '*/main']], 
       //          userRemoteConfigs: [[url: 'https://github.com/Papipaps/mspr-api-webshop.git']]])
     // }
   // }
    
    stage('Maven Test') {
            steps {
                sh './mvnw clean test'
                archiveArtifacts artifacts: 'target/surefire-reports/**'
            }
        }
    
    stage('Build') {
          steps {
              sh 'mvn clean package -DskipTests' 
              archiveArtifacts artifacts: 'target/*.jar'
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
