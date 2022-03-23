import org.mauro.config.Constants
import org.mauro.templating.BuilderRetriever

def public createProjetIfNotExists (artifactId) {
    if (!isprojectExists(artifactId)) {
        createProject(artifactId)
    }
}

def public isprojectExists (artifactId) {
    items = sh(script: "curl -X GET -H 'Content-Type: application/json' -u '${getCredentials()}' '${Constants.getSonarHost()}/api/projects/search?projects=${artifactId}' | jq -r '.components | length'", returnStdout: true)
    return items == 0
}

def public getCredentials () {
    validateEnvVars()
    return "${SONAR_CRED_USR}:${SONAR_CRED_PSW}"
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
    sh "curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -u '${getCredentials()}' -d 'project=${artifactId}&name=${artifactId}' '${Constants.getSonarHost()}/api/projects/create?'"
}

def public pushSonarAnalysis (artifactId) {
    BuilderRetriever.getBuilder().pushSonarAnalysis(artifactId)
}

def public qualityGate (artifactId) {
    def qualityGateStatus = sh(script: "curl -X GET -H 'Content-Type: application/json' -u '${getCredentials()}' '${Constants.getSonarHost()}/api/qualitygates/project_status?projectKey=${artifactId}' | jq -r '.projectStatus.status'", returnStdout: true).trim()
    sh "echo 'status ${qualityGateStatus}'"
    if ("${qualityGateStatus}" != 'OK') {
        error('Quality gate fail...!')
    }
}
