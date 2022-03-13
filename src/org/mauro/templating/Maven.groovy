package org.mauro.templating

def getAppVersion () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
}

def getAppServiceName () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.name -q -DforceStdout', returnStdout: true).trim()
}

def getArtifactId () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout', returnStdout: true).trim()
}

def getgroupId () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout', returnStdout: true).trim()
}

def getTemplate () {
    def constants = new org.mauro.Constants()
    return constants.getMavenTemplate()
}

def getAgent () {
    def constants = new org.mauro.Constants()
    return constants.getMavenAgent()
}

def build () {
    sh 'mvn clean package'
}

def getOutFolder () {
    return 'target/'
}

def publishTestCoverageReport () {
    def tools = new org.mauro.Tools()
    tools.publishingHTML('code coverage', 'code coverage report', 'target/jacoco-report/', 'index.html', true)
}

def pushSonarAnalyse (project, token, repository) {
    def constants = new org.mauro.Constants()
    sonarHost = constants.getSonarHost()
    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${project} -Dsonar.host.url=${sonarHost} -Dsonar.login=${token} -Dsonar.projectName=${repository}"
}

return this