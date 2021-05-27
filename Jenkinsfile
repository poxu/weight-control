pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    environment {
        BOT_TOKEN = credentials('bot-token')
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnw -B -DskipTests clean package'
            }
        }
        stage('Stop docker') {
            steps {
                sh 'docker-compose --project-name=weight-control-app down'
            }
        }
        stage('Start docker') {
            steps {
                sh "docker-compose -e BOT_TOKEN=$BOT_TOKEN --project-name=weight-control-app up -d --build"
            }
        }
    }
}
