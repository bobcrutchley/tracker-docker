node("build") {
    deleteDir()
    def ssh = { cmd -> sh cmd }
    echo "Running on build node"
    try {
        def trainerApp = "docker run -p 8080:8080 --name trainer-app --link trainer-mysql:mysql -d trainer/trainer-tracker"
        checkout scm
        sh "git branch"
        sh "mvn clean package docker:build"
        sh "docker run --name trainer-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=trainer -e MYSQL_USER=trainer_user -e MYSQL_PASSWORD=trainer_pass -d mysql:5.6"
    } catch(e) {

    } finally {
        stopAllDockerContainers(ssh)
        removeAllDockerContainers(ssh)
    }
    ssh "docker ps"
}

static def stopAllDockerContainers(Closure ssh) {
    ssh "docker docker stop \$(docker ps -aq)"
}

static def removeAllDockerContainers(Closure ssh) {
    ssh "docker rm \\\$(docker ps -aq)"
}