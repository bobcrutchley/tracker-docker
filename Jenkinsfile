node("build") {
    deleteDir()
    echo "Running on build node"
    def trainerApp = "docker run -p 8080:8080 --name trainer-app --link trainer-mysql:mysql -d trainer/trainer-tracker"
    checkout scm
    sh "git branch"
    sh "mvn clean package"
    sh "ls -l target"
}