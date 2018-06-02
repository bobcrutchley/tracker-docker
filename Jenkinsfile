node("build") {
    echo "Running on build node"
    def trainerApp = "docker run -p 8080:8080 --name trainer-app --link trainer-mysql:mysql -d trainer/trainer-tracker"
    checkout scm
    sh "ls -l"
    sh "mvn clean package docker:build"
}