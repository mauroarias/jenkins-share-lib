import org.mauro.config.Constants






















def public validateEnvVars () {
    if ("${SONAR_USER}" == '') {   
        error("sonar used not defined...!")
    }
    if ("${SONAR_PASSWORD}" == '') {   
        error("sonar password not defined...!")
    }
}

def public createProject (project, repository) {
    sonarHost = Constants.getJenkinsHost()
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u ${getCredentials()} -d 'project=${project}&name=${repository}' '${sonarHost.getSonarHost()}/api/projects/create?'"
}

def public isprojectExists (project, repository) {
    found = sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${sonarHost.getSonarHost()}/api/projects/search?projects=${project}' | jq -r '.components[] | .name' | grep '${repository}'", returnStdout: true)
    return found != ''
}

def public createProjetIfNotExists (project, repository) {
    if (!isprojectExists("${project}", "${repository}")) {
        createProject("${project}", "${repository}")
    }
}

def public getToken () {
    return sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${sonarHost.getSonarHost()}/api/user_tokens/generate?name=token1' | jq -r '.token'", returnStdout: true)
}

def public pushSonarAnalyse (type, project, repository) {
    token = getToken()
    pushSonarAnalyse("${project}", "${token}", "${repository}") {
}

def public getCredentials () {
    return "'${SONAR_USER}:${SONAR_PASSWORD}'"
}
