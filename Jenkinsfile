node {
  checkout scm
  env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
  stage('Compile') {
    dir('webapp') {
      sh 'mvn clean package -DskipTests'
      junit '**/target/surefire-reports/*.xml'
    })
  }

  stage('Create Docker Image') {
    dir('webapp') {
      docker.build("arungupta/docker-jenkins-pipeline:${env.BUILD_NUMBER}").push()
    }
  }

  stage ('Run Application') {
    docker.image("arungupta/couchbase").withRun(
      c -> docker.image("arungupta/docker-jenkins-pipeline:${env.BUILD_NUMBER}").withRun()
    )
  }

  stage('Run Tests') {
    docker.image("arungupta/couchbase").withRun(
      c -> sh 'mvn test'
    )
  }
}
