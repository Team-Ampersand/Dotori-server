pipeline{
    agent any

    stages {

        stage('Clone repository') {
            steps{
                checkout scm
            }
        }


        stage('Application_Config'){
            steps{
                sh '''sudo cp ${APPLICATION} ${APPLICATION_CONFIG}'''
            }
        }

//         stage('Build BackEnd') {
//             sh'''
//             sudo ./gradlew clean build --exclude-task test
//             '''
//         }
//
//         stage('reset'){
//             sh'''docker stop ${DOTORI_APP}_1 || true'''
//             sh'''docker rm ${DOTORI_APP}_1 || true'''
//             sh'''docker rmi ${DOTORI_APP}:latest || true'''
//             sh'''docker stop ${DOTORI_REDIS}_1 || true'''
//             sh'''docker rm ${DOTORI_REDIS}_1 || true'''
//         }
//     }
//
//
//      post {
//           cleanup{
//              deleteDir()
//
//              dir("${workspace}@tmp") {
//                  deleteDir()
//              }
//
//              dir("${workspace}@script") {
//                  deleteDir()
//              }
//          }
//      }
//
//
//
//      stage('docker-compose'){
//          sh'''docker-compose up --build -d'''
//      }

}
