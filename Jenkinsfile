pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnw -B -DskipTests clean package'
            }
        }
        stage('Test'){
            steps {
                sh './mvnw test'
            }
        }
        stage('Deploy'){
            steps {
                sh '''
                    cp target/weight-control-0.0.1-SNAPSHOT.jar /home/riptor/projects/jenkins-install/deploy
                    cd /home/riptor/projects/jenkins-install/deploy
                    test -f application.pid && kill $(application.pid)
                    nohup java -jar weight-control-0.0.1-SNAPSHOT.jar 2>&1
                '''
            }
        }
    }
}
