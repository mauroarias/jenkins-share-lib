def validateEnvVars () {
    if ("${SONAR_USER}" == '') {   
        error("sonar used not defined...!")
    }
    if ("${SONAR_PASSWORD}" == '') {   
        error("sonar password not defined...!")
    }
}

def createProject (project, repository) {
    sonarHost = Constants.getJenkinsHost()
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u ${getCredentials()} -d 'project=${project}&name=${repository}' '${sonarHost.getSonarHost()}/api/projects/create?'"
}

def isprojectExists (project, repository) {
    found = sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${sonarHost.getSonarHost()}/api/projects/search?projects=${project}' | jq -r '.components[] | .name' | grep '${repository}'", returnStdout: true)
    return found != ''
}

def createProjetIfNotExists (project, repository) {
    if (!isprojectExists("${project}", "${repository}")) {
        createProject("${project}", "${repository}")
    }
}

def getToken () {
    return sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${sonarHost.getSonarHost()}/api/user_tokens/generate?name=token1' | jq -r '.token'", returnStdout: true)
}

def pushSonarAnalyse (type, project, repository) {
    token = getToken()
    pushSonarAnalyse("${project}", "${token}", "${repository}") {
}

def getCredentials () {
    return "'${SONAR_USER}:${SONAR_PASSWORD}'"
}
