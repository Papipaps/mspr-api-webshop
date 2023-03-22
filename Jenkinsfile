pipeline {
  agent any
  
  stages {
    //stage('Checkout') {
     // steps {
      //  checkout([$class: 'GitSCM', branches: [[name: '*/main']], 
       //          userRemoteConfigs: [[url: 'https://github.com/Papipaps/mspr-api-webshop.git']]])
     // }
   // }
    
    stage('Maven Test') {
            steps {
                sh 'mvn test'
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
