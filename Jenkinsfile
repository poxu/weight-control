pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    environment {
        BOT_TOKEN = credentials('bot-token')
    }
    stages {
        stage('Stop docker') {
            steps {
                sh 'docker-compose --project-name=weight-control-app down'
            }
        }
        stage('Build') {
            steps {
                sh './mvnw -B -DskipTests clean package'
            }
        }
        stage('Start docker') {
            steps {
                sh "BOT_TOKEN=$BOT_TOKEN docker-compose --project-name=weight-control-app up -d --build"
            }
        }
    }
}
