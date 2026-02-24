pipeline {
    agent any

    environment {
        APP_SERVER = "ec2-user@16.170.238.93"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                credentialsId: 'github-creds',
                url: 'https://github.com/SaiManojYanamula/nextgenmotor.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sshagent(['app-server-ssh']) {
                    sh '''
                    scp -o StrictHostKeyChecking=no target/*.jar $APP_SERVER:/home/ec2-user/
                    ssh -o StrictHostKeyChecking=no $APP_SERVER "pkill -f jar || true"
                    ssh -o StrictHostKeyChecking=no $APP_SERVER "nohup java -jar /home/ec2-user/*.jar > app.log 2>&1 &"
                    '''
                }
            }
        }
    }
}