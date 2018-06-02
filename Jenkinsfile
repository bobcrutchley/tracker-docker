node("build") {
    deleteDir()
    def ssh = { cmd -> sh cmd }
    echo "Running on build node"
    try {
        checkout scm
        stage("build and test") { sh "mvn clean package docker:build" }
        stage("mysql") { dockerRunMysql(ssh) }
        stage("trainer app") { dockerRunTrainerApp(ssh) }
        while(!sh(returnStdout: true, script: "docker logs trainer-app").contains("Started App")) {
            sleep 1000
        }
        stage("integration test") { sh "mvn integration-test" }
    } catch(e) {

    } finally {
        stage("clean up") {
            dockerRemoveAllOldTrainerAppImages(ssh)
            dockerStopAllContainers(ssh)
            dockerRemoveAllContainers(ssh)
        }
    }
}

static def dockerRunTrainerApp(Closure ssh) {
    ssh "docker run -p 8080:8080 --name trainer-app --link trainer-mysql:mysql -d trainer/trainer-tracker"
}

static def dockerRunMysql(Closure ssh) {
    ssh "docker run --name trainer-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=trainer -e MYSQL_USER=trainer_user -e MYSQL_PASSWORD=trainer_pass -d mysql:5.6"
}

static def dockerStopAllContainers(Closure ssh) {
    ssh "docker stop \$(docker ps -aq)"
}

static def dockerRemoveAllContainers(Closure ssh) {
    ssh "docker rm \$(docker ps -aq)"
}

static def dockerRemoveAllOldTrainerAppImages(Closure ssh) {
    ssh "docker rmi \$(docker images | grep none | awk '{ print \$3 }')"
}
