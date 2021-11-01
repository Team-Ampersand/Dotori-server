node {
     stage('Clone repository') {
         checkout scm
     }

     stage('Application_Config'){
        sh '''cp /home/ngj/DotoriConfig/application.yml /var/lib/jenkins/workspace/Dotori-test-server/src/main/resources'''
     }

     stage('Build BackEnd') {
        sh'''
        sudo ./gradlew clean build --exclude-task test
        '''
     }

     stage('reset'){
        sh'''docker stop dotori-test-server_app_1 || true'''
        sh'''docker rm dotori-test-server_app_1 || true'''
        sh'''docker rmi dotori-test-server_app:latest || true'''
     }

     stage('docker-compose'){
        sh '''docker-compose up -d'''
     }
}