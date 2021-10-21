node {
     stage('Clone repository') {
         checkout scm
     }

     stage('pwd'){
        sh'''
        pwd
        '''
     }

     stage('Application_Config'){
        sh '''cp /home/ngj/DotoriConfig/application.yml /var/lib/jenkins/workspace/Dotori-test-server/src/main/resources'''
     }

     stage('Build BackEnd') {
        sh'''
        ./gradlew clean build --exclude-task test
        '''
     }

     stage('Build image') {
        app = docker.build("${REPOSITORY_NAME}/${CONTAINER_NAME}:latest")
     }

     stage('Push image') {
        docker.withRegistry('https://registry.hub.docker.com', 'Dotori-docker-hub') {
            app.push()
        }
     }

     stage('docker-compose'){
        sh '''nohup docker-compose-env &'''
     }

// //      stage('Code Deploy') {
// //          sh '''docker stop ${CONTAINER_NAME}1 || true && docker rm ${CONTAINER_NAME}1 || true''' // 컨테이너1 rm
// //          sh '''docker stop ${CONTAINER_NAME}2 || true && docker rm ${CONTAINER_NAME}2 || true''' // 컨테이너2 rm
// //          sh '''docker stop ${CONTAINER_NAME}3 || true && docker rm ${CONTAINER_NAME}3 || true''' // 컨테이너3 rm
// //          sh '''docker rmi -f `docker images | awk '$1 ~ /springboot2/ {print $3}'`''' // image 삭제
// //          sh '''docker run -d -p ${PORT1}:${PORT1} --name ${CONTAINER_NAME}1 ${REPOSITORY_NAME}ㅣ/${CONTAINER_NAME}:latest''' // 컨테이너 1 // local : container
// //          sh '''docker run -d -p ${PORT2}:${PORT1} --name ${CONTAINER_NAME}2 ${REPOSITORY_NAME}/${CONTAINER_NAME}:latest''' // 컨테이너 2
// //          sh '''docker run -d -p ${PORT3}:${PORT1} --name ${CONTAINER_NAME}3 ${REPOSITORY_NAME}/${CONTAINER_NAME}:latest''' // 컨테이너 3
// //      }
}