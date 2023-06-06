pipeline{
    agent any
    tools{
        maven 'Maven_3_9_2'
    }
    stages{
        stage('Build Maven'){
            steps{
                git url: 'https://github.com/akshataxx/Prime-Number-Generator-SpringBoot-Pub-Sub-Queue', branch: 'master'
                bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t akshataxx/springboot-app:v7 . '
                }
            }
        }
        stage('Push image to hub'){
            steps{
                script{
                    bat 'docker login --username akshataxx --password dckr_pat_B_ed4Ylrb4dQG5n_D2RP9dtNHEc'
                    bat 'docker push akshataxx/springboot-app:v7'
                }
            }
        }
    }
}