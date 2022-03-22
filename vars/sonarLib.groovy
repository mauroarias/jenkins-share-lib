import org.mauro.config.Constants
import org.mauro.templating.BuilderRetriever

def public createProjetIfNotExists (artifactId) {
    if (!isprojectExists(artifactId)) {
        createProject(artifactId)
    }
}

def public isprojectExists (artifactId) {
    found = sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${Constants.getJenkinsHost()}/api/projects/search?projects=${projectName}' | jq -r '.components[] | .name' | grep '${artifactId}'", returnStdout: true)
    return found != ''
}

def public getCredentials () {
    validateEnvVars()
    return "'${SONAR_CRED_USR}:${SONAR_CRED_PSW}'"
}

def validateEnvVars () {
    if ("${SONAR_CRED_USR}" == '') {   
        error('sonar used not defined...!')
    }
    if ("${SONAR_CRED_PSW}" == '') {   
        error('sonar password not defined...!')
    }
}

def public createProject (artifactId) {
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u ${getCredentials()} -d 'project=${artifactId}&name=${artifactId}' '${Constants.getJenkinsHost()}/api/projects/create?'"
}

def public pushSonarArtifact (artifactId) {
    BuilderRetriever.getBuilder().pushSonarArtifact(artifactId)
}

def public qualityGate (artifactId) {
    def qualityGateStatus = sh(script: "curl -X GET -H 'Content-Type: application/json' -u ${getCredentials()} '${Constants.getJenkinsHost()}/api/qualitygates/project_status?projectKey=${artifactId}' | jq -r '.projectStatus.status'", returnStdout: true)
    if (!"${qualityGateStatus}".equals('OK')) {
        error('Quality gate fail...!')
    }
}
