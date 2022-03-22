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

def public createProject (artifactId) {
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u ${getCredentials()} -d 'project=${artifactId}&name=${artifactId}' '${Constants.getJenkinsHost()}/api/projects/create?'"
}

def public pushSonarArtifact (artifactId) {
    BuilderRetriever.getBuilderInst().pushSonarArtifact(artifactId)
}

def public qualityGate (projectName) {
    return sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${Constants.getJenkinsHost()}/api/qualitygates/project_status?projectKey=${projectName}' | jq -r '.projectStatus.status'", returnStdout: true)
}
