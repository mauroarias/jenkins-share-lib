package org.mauro.templating

import org.mauro.config.Constants

def public getAppVersion () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
}

def public getAppServiceName () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.name -q -DforceStdout', returnStdout: true).trim()
}

def public getArtifactId () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout', returnStdout: true).trim()
}

def public getgroupId () {
    return sh(script: 'mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout', returnStdout: true).trim()
}

def public getTemplate () {
    return Constants.getMavenTemplate()
}

def public getAgent () {
    return Constants.getMavenAgent()
}

def public build () {
    sh 'mvn clean package'
}

def public getOutFolder () {
    return 'target/'
}

def public publishTestCoverageReport () {
    def tools = new org.mauro.Tools()
    tools.publishingHTML('code coverage', 'code coverage report', 'target/jacoco-report/', 'index.html', true)
}

def public pushSonarAnalyse (project, token, repository) {
    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${project} -Dsonar.host.url=${Constants.getSonarHost()} -Dsonar.login=${token} -Dsonar.projectName=${repository}"
}

return this