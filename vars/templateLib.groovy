import org.mauro.config.Constants
import org.mauro.git.GitRetriever

def public getTemplates () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo '${templateTypeList}' | yq '.types[] | .fullName'", returnStdout: true)
}

def public getDefaultAgent () {
    return Constants.getDefaultAgent()
}

def public gettingGitRepository (gitDstRemote, projectName, serviceName) {
    GitRetriever.configGitRep(gitDstRemote)
    GitRetriever.getGitInst().createProjectIfNotExits(this, projectName)
    if (GitRetriever.getGitInst().isRepositoryExits(this, serviceName)) {
        error('repository already exits...!')
    }
}

def public applyGitRepository (gitDstRemote, serviceName, templateFullName, projectName) {
    branch=getTemplatebranch(templateFullName)
    template=getTemplate(templateFullName)
    repoTemplate=Constants.getRepoTemplate()
    sh "echo 'template ${template}'"
    sh "echo 'branch ${branch}'"
    sh "echo 'cloning ${serviceName} from repo ${repoTemplate}'"
    sh "rm -rf ./${serviceName}"
    GitRetriever.getGitInst().cloneRepo(this, branch, repoTemplate, template, serviceName)
    sh "./${serviceName}/prepare.sh ${serviceName}"
    sh "rm ./${serviceName}/prepare.sh"
    GitRetriever.getGitInst().createRepo(this, serviceName, projectName)
    GitRetriever.getGitInst().initRepo(this, serviceName)
    GitRetriever.getGitInst().commitAndPushRepo(this)
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

def getCiType () {
    def ciLibrary=sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true)
    if ("${ciLibrary}" == null || "${ciLibrary}".equals('')) {
        error('ci library must be defined...!')
    }
    return "${ciLibrary}"
}

def getCiVersion () {
    def ciVersion=sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
    if ("${ciVersion}" == null || "${ciVersion}".equals('')) {
        error('ci version must be defined...!')
    }
    return "${ciVersion}"
}

def getCdType () {
    def cdLibrary=sh(script: "cat ./manifest.yaml | yq -o=x '.cd.type'", returnStdout: true)
    if ("${cdLibrary}" == null || "${cdLibrary}".equals('')) {
        error('ci library must be defined...!')
    }
    return "${cdLibrary}"
}

def getCdVersion () {
    def cdVersion=sh(script: "cat ./manifest.yaml | yq -o=x '.cd.version'", returnStdout: true)
    if ("${cdVersion}" == null || "${cdVersion}".equals('')) {
        error('ci version must be defined...!')
    }
    return "${cdVersion}"
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