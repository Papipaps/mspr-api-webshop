pipeline {
  agent any
  stages {
    stage('Checkout Code') {
      steps {
        git(url: 'https://github.com/Papipaps/mspr-api-webshop.git', branch: 'main')
      }
    }

    stage('Build') {
      steps {
        sh 'docker build -f mspr-api-webshop/Dockerfile . -t tag/name'
      }
    }

    stage('Login to GCP') {
      steps {
        echo '\'Login to GCP\''
      }
    }

    stage('Pushing new image to Kubernetes') {
      steps {
        echo 'Pushing image to kubernetes'
        sh '//docker push tag/image'
      }
    }

  }
}