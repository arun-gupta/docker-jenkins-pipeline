node {
  checkout scm
  env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
  stage('Package') {
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
    try {
      // sh 'docker run -d --name db -p 8091-8093:8091-8093 -p 11210:11210 arungupta/oreilly-couchbase:latest'

      sh "docker run -e DB_URI=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' db` arungupta/docker-jenkins-pipeline:${env.BUILD_NUMBER}"
      //dir ('webapp') {
      //  sh 'mvn exec:java -DskipTests'
      //}
    } catch (error) {
    } finally {
      //sh 'docker-compose stop db'
      //sh 'docker-compose rm db'
    }
  }

  stage('Run Tests') {
    try {
      //db = docker.image("arungupta/couchbase").withRun("-d --name db --service-ports db")
      dir('webapp') {
        //sh "mvn test -DDB_URI=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' db`"
        sh "mvn test"
      }
    } catch (error) {

    } finally {
      //db.stop()
      junit '**/target/surefire-reports/*.xml'
    }
  }
}
