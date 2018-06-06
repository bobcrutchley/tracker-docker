node("build") {
    deleteDir()
    def ssh = { cmd -> sh(returnStdout: true, script: cmd) }
    echo "Running on build node"
    try {
        checkout scm
        stage("build and test") { sh "mvn clean package docker:build" }
        stage("mysql") { dockerRunMysql(ssh) }
        stage("trainer app") { dockerRunTrainerApp(ssh) }
        while(!sh(returnStdout: true, script: "docker logs trainer-app").contains("Started App")) {
            sleep 1
        }
        stage("integration test") { sh "mvn integration-test" }
        sh "docker save trainer/trainer-tracker > $workspace/trainer-tracker.tar"
        archiveArtifacts "trainer-tracker.tar"
    } catch(e) {
        println "Build error:\n $e"
    } finally {
        stage("clean up") {
            dockerStopAllContainers(ssh)
            dockerRemoveAllContainers(ssh)
            dockerRemoveAllOldTrainerAppImages(ssh)
        }
        deleteDir()
    }
}

node("master") {
//    def ssh = { cmd -> sh(returnStdout: true, script: cmd) }
//    sh "scp build:/tmp/trainer-tracker.tar $workspace/trainer-tracker.tar"
//    sh "scp $workspace/trainer-tracker.tar uat:/tmp/trainer-tracker.tar"
}

node("uat") {
//    sh "docker load /tmp/trainer-tracker.tar"
//    sh "docker images"
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
    def oldImages = ssh("docker images | grep none | awk '{ print \$3 }'").replaceAll("\n", " ")
    if (oldImages) ssh "docker rmi $oldImages"
}
