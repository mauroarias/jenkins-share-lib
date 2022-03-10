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

return this