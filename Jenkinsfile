node("build") {
    deleteDir()
    def ssh = { cmd -> sh cmd }
    echo "Running on build node"
    try {
        checkout scm
        sh "git branch"
        sh "mvn clean package docker:build"
        dockerRunMysql(ssh)
        dockerRunTrainerApp(ssh)
        ssh "docker ps"
    } catch(e) {

    } finally {
        stopAllDockerContainers(ssh)
        removeAllDockerContainers(ssh)
    }
    ssh "docker ps"
}

static def dockerRunTrainerApp(Closure ssh) {
    ssh "docker run -p 8080:8080 --name trainer-app --link trainer-mysql:mysql -d trainer/trainer-tracker"
}

static def dockerRunMysql(Closure ssh) {
    ssh "docker run --name trainer-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=trainer -e MYSQL_USER=trainer_user -e MYSQL_PASSWORD=trainer_pass -d mysql:5.6"
}

static def stopAllDockerContainers(Closure ssh) {
    ssh "docker stop \$(docker ps -aq)"
}

static def removeAllDockerContainers(Closure ssh) {
    ssh "docker rm \$(docker ps -aq)"
}
