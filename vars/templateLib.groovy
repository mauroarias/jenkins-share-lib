def getTemplates () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo '${templateTypeList}' | yq '.types[] | .fullName'", returnStdout: true)
}

def getDefaultAgent () {
    return constantInst.getDefaultAgent()
}







def getAppVersion (type) {
    sh "echo 'get App Version with ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getAppVersion()
        default:
            error('template no supported...!')
    }
}

def getAppServiceName (type) {
    sh "echo 'get service name with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getAppServiceName()
        default:
            error('template no supported...!')
    }
}

def getArtifactId (type) {
    sh "echo 'get artifact Id with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getArtifactId()
        default:
            error('template no supported...!')
    }
}

def getgroupId (type) {
    sh "echo 'get group id with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getgroupId()
        default:
            error('template no supported...!')
    }
}

def getAgent (type) {
    sh "echo 'get Agent with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getAgent()
        default:
            error('template no supported...!')
    }
}

def getAgentByTemplate (type) {
    sh "echo 'get Agent with type ${type}'"
    switch(type) {
        case 'maven java8':
        case 'maven java11':
            def maven = new org.mauro.templating.Maven()
            return maven.getAgent()
        default:
            error('template no supported...!')
    }
}

def getBranch (type) {
    sh "echo 'get branch with type ${type}'"
    switch(type) {
        case 'maven java8':
            return 'wip-0.1.0'//'java8'
        case 'maven java11':
            return 'java11'
        default:
            error('template no supported...!')
    }
}

def getTemplateType (type) {
    sh "echo 'get template with type ${type}'"
    switch(type) {
        case 'maven java8':
        case 'maven java11':
            def maven = new org.mauro.templating.Maven()
            return maven.getTemplate()
        default:
            error('template no supported...!')
    }
}

def getCiType () {
    type = sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true)
    return "${type}"
}

def getCiVersion () {
    return sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
}

def getCdType () {
    type = sh(script: "cat ./manifest.yaml | yq -o=x '.cd.type'", returnStdout: true)
    return "${type}"
}

def getCdVersion () {
    return sh(script: "cat ./manifest.yaml | yq -o=x '.cd.version'", returnStdout: true)
}

def getCiPipeline () {
    return 'pipelineCi'
}

def build (type) {
    sh "echo 'build with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.build()
        default:
            error('template no supported...!')
    }
}

def getOutFolder (type) {
    sh "echo 'get Out Folder with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.getOutFolder()
        default:
            error('template no supported...!')
    }
}

def publishTestCoverageReport (type) {
    sh "echo 'publish test coverage report with type ${type}'"
    switch(type) {
        case 'maven':
            def maven = new org.mauro.templating.Maven()
            return maven.publishTestCoverageReport()
        default:
            error('template no supported...!')
    }
}