pipeline{
    agent any

    environment {
        PATH = "$PATH:/usr/bin"
    }

    stages {

        stage('Clone repository') {
            steps{
                checkout scm
            }
        }


        stage('Application_Config'){
            steps{
                sh '''sudo rm -rf ${DELETE_APPLICATION1}'''
                sh '''sudo rm -rf ${DELETE_APPLICATION2}'''
                sh '''sudo cp ${APPLICATION} ${APPLICATION_CONFIG}'''
            }
        }

        stage('Build BackEnd') {
            steps{
                sh '''sudo ./gradlew clean build --exclude-task test'''
            }
        }

        stage('reset'){
            steps{
                sh'''docker stop ${DOTORI_APP}_1 || true'''
                sh'''docker rm ${DOTORI_APP}_1 || true'''
                sh'''docker rmi ${DOTORI_APP}:latest || true'''
                sh'''docker stop ${DOTORI_REDIS}_1 || true'''
                sh'''docker rm ${DOTORI_REDIS}_1 || true'''
            }
        }

        stage('docker-compose'){
            steps{
                sh'''/usr/bin/docker-compos up --build -d'''
            }
        }
    }

}
