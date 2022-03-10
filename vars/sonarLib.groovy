def validateEnvVars () {
    if ("${SONAR_USER}" == '') {   
        error("sonar used not defined...!")
    }
    if ("${SONAR_PASSWORD}" == '') {   
        error("sonar password not defined...!")
    }
}

def createProject() {
    def constants = new org.mauro.Constants()
    sonarHost = constants.getJenkinsHost()
    sh "curl -i -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u 'admin:passwd' -d 'project=Our&organization=test&name=some' '${sonarHost.getSonarHost()}http://localhost:9000/api/projects/create?'"
}