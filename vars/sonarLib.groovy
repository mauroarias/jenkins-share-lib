import org.mauro.config.Constants
import org.mauro.templating.BuilderRetriever

def public createProjetIfNotExists (projectName, artifactId) {
    if (!isprojectExists(projectName, artifactId)) {
        createProject(projectName, artifactId)
    }
}

def public isprojectExists (projectName, artifactId) {
    found = sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${Constants.getJenkinsHost()}/api/projects/search?projects=${projectName}' | jq -r '.components[] | .name' | grep '${artifactId}'", returnStdout: true)
    return found != ''
}

def public getCredentials () {
    validateEnvVars()
    return "'${SONAR_CRED_USR}:${SONAR_CRED_PSW}'"
}

def validateEnvVars () {
    if ("${SONAR_CRED_USR}" == '') {   
        error("sonar used not defined...!")
    }
    if ("${SONAR_CRED_PSW}" == '') {   
        error("sonar password not defined...!")
    }
}

def public createProject (projectName, artifactId) {
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u ${getCredentials()} -d 'project=${projectName}&name=${artifactId}' '${Constants.getJenkinsHost()}/api/projects/create?'"
}

def public pushSonarArtifact (projectName, artifactId) {
    token = getToken()
    BuilderRetriever.getBuilderInst().pushSonarArtifact(projectName, token, artifactId)
}

def getToken () {
    return sh(script: "curl -X POST -H 'Content-Type: application/json' -u ${getCredentials()} '${Constants.getJenkinsHost()}/api/user_tokens/generate?name=token1' | jq -r '.token'", returnStdout: true)
}


