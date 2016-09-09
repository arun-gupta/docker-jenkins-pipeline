node {
  checkout scm
  env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
  stage('Compile') {
    dir('webapp') {
      sh 'mvn clean package -DskipTests'
    }
  }

  stage('Create Docker Image') {
    dir('webapp') {
      //docker.build("arungupta/docker-jenkins-pipeline:${env.BUILD_NUMBER}").push()
      docker.build("arungupta/docker-jenkins-pipeline:${env.BUILD_NUMBER}")
    }
  }

  stage ('Run Application') {
    sh 'docker-compose run -d --name db --service-ports db'
    sh "DB_URI=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' db`"
    sh "docker-compose run -e DB_URI=$DB_URI -e BUILD_NUMBER=${env.BUILD_NUMBER} app2"
    sh 'docker-compose stop db'
    sh 'docker-compose rm db'
  }

  stage('Run Tests') {
    db = docker.image("arungupta/couchbase").withRun("-d --name db --service-ports db")
    dir('webapp') {
      sh "DB_URI=docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' db"
      sh "mvn test -DDB_URI=$DB_URI db)"
    }
    db.stop()
    junit '**/target/surefire-reports/*.xml'
  }
}
