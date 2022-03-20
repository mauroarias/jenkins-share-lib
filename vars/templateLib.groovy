import org.mauro.config.Constants

// def public getDefaultAgent () {
//     return Constants.getDefaultAgent()
// }

def public getTemplates () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo '${templateTypeList}' | yq '.types[] | .fullName'", returnStdout: true)
}

def public getTemplateParameter (templateFullName, parameter) {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo '${templateTypeList}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .${parameter}'", returnStdout: true)
}

def public getTemplate (templateFullName) {
    return getTemplateParameter(templateFullName, "template")
}

def public getTemplateName (templateFullName) {
    return getTemplateParameter(templateFullName, "name")
}

def public getTemplateAgent (templateFullName) {
    return getTemplateParameter(templateFullName, "agent")
}

def public getTemplatebranch (templateFullName) {
    return getTemplateParameter(templateFullName, "branch")
}

def public gettingGitRepository (dstRemote, projectName, serviceName) {
    sh "strategyHandler.sh -r ${dstRemote} -c gettingRepository -j ${projectName} -s ${serviceName}"
}

def public applyGitRepository (dstRemote, serviceName, templateFullName) {
    branch=getTemplatebranch(templateFullName)
    template=getTemplate(templateFullName)
    sh "echo 'template ${template}'"
    sh "echo 'branch ${branch}'"
    sh "strategyHandler.sh -r ${dstRemote} -c applyTemplate -t ${template} -s ${serviceName} -b ${branch}"
}

def getCiType () {
    return sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true)
}

def getCiVersion () {
    sh "ls -ls"
    return sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
}

def getCdType () {
    return sh(script: "cat ./manifest.yaml | yq -o=x '.cd.type'", returnStdout: true)
}

def getCdVersion () {
    return sh(script: "cat ./manifest.yaml | yq -o=x '.cd.version'", returnStdout: true)
}

def getCiPipeline () {
    return Constants.getPipelineCi()
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