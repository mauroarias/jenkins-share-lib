import org.mauro.config.Config
import org.mauro.config.Constants
import org.mauro.git.GitRetriever
import org.mauro.templating.BuilderRetriever
import org.mauro.Tools













def public getTemplates () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo '${templateTypeList}' | yq '.types[] | .fullName'", returnStdout: true)
}

def public config (templateFullName) {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    Config.configByTemplate(this, templateTypeList, templateFullName)
}

def public configUsingManifest () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    Config.configByManifest(this, templateTypeList)
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
    branch=Config.getBranch()
    template=Config.getTemplate()
    repoTemplate=Constants.getRepoTemplate()
    sh "echo 'template ${template}'"
    sh "echo 'branch ${branch}'"
    sh "echo 'cloning ${serviceName} from repo ${repoTemplate}'"
    sh "rm -rf ./${serviceName}"
    GitRetriever.getGitInst().cloneRepo(this, branch, repoTemplate, template, serviceName)
    dir(serviceName) {
        sh "./prepare.sh ${serviceName}"
        sh "rm ./prepare.sh"
        GitRetriever.getGitInst().createRepo(this, serviceName, projectName)
        def template = libraryResource 'org/mauro/templates/JenkinsfilePipelineJobWithLibTemplate'
        Tools.createJenkinsPipelineFileWithLib(this, template, Config.getVersion(), Constants.getPipelineCi())
        GitRetriever.getGitInst().initRepo(this, serviceName)
        GitRetriever.getGitInst().commitAndPushRepo(this)
    }
}

def public getTemplateAgent () {
    return Config.getAgent()
}

def public getAppVersion () {
    return BuilderRetriever.getBuilder().getAppVersion()
}

def public getAppServiceName () {
    return BuilderRetriever.getBuilder().getAppServiceName()
}

def public getArtifactId () {
    return BuilderRetriever.getBuilder().getArtifactId()
}

def public getgroupId () {
    return BuilderRetriever.getBuilder().getgroupId()
}

def public build (app) {
    BuilderRetriever.getBuilder().build(app)
}
