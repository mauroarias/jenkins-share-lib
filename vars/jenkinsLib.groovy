import org.mauro.config.Config
import org.mauro.config.Constants
import org.mauro.Tools

def public downloadJenkinsCli () {
    sh "wget '${Constants.getJenkinsHost()}/jnlpJars/jenkins-cli.jar'"
}

def public getprojects () {
    return sh(script: "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket list-jobs | grep 'PRJ-' | sed 's/PRJ-//g'", returnStdout: true)
}

def public createProjectIfNotExits (projectName) {
    def template = libraryResource 'org/mauro/templates/createProject.xml'
    configFileName="./config${currentBuild.startTimeInMillis}.xml" 
    sh "echo 'creating project ${projectName}'"
    sh "echo '${template}' > ${configFileName}"
    sh "sed -i 's/<description>/<description>${projectName}/' ${configFileName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${projectName} < ${configFileName}"
    sh "rm ${configFileName}"
}

def public createJenkinsMultibranchJobWithLib (gitDstRemote, repository, projectName, serviceName) {
    template = ''
    owner=''
    url=''
    if ("${gitDstRemote}" == 'gitHub') {
        template = libraryResource 'org/mauro/templates/createMultibranchJobWithLibGitHub.xml'
        owner="${GIT_HUB_CRED_USR}"
        url="https://github.com/${GIT_HUB_CRED_USR}/${serviceName}"
    } else if ("${gitDstRemote}" == 'bitBucket') {
        template = libraryResource 'org/mauro/templates/createMultibranchJobWithLibBitBucket.xml'
        owner="${BIT_BUCKET_CRED_USR}"
        url="https://bitbucket.org/${BIT_BUCKET_CRED_USR}/${serviceName}"
    } else {
        error("git remote not supported")
    }
    configName="./config${currentBuild.startTimeInMillis}.xml"
    sh "echo 'creating multibranch job ${repository}'"
    sh "echo '${template}' > ${configName}"
    sh "sed -i 's!__name__!${serviceName}!g; s!__repository__!${repository}!g; s!__repository_owner__!${owner}!g; s!__repository_url__!${url}!g' ${configName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${projectName}/${repository} < ${configName}"
    sh "rm ${configName}"
}

def public createPipelineJobWithLib (name, projectName, serviceName) {
    def pipepileTemplate = libraryResource 'org/mauro/templates/JenkinsfilePipelineJobWithLibTemplate'
    file = Tools.createJenkinsPipelineFileWithLib(this, pipepileTemplate, Config.getCdVersion(), Config.getCdType())
    configName="./config${currentBuild.startTimeInMillis}.xml" 
    def template = libraryResource 'org/mauro/templates/createPipelineJobTemplate.xml'
    sh "echo 'creating pipeline job ${name}'"
    sh "echo '${template}' | grep -B 100 '__PIPELINE__' | head -n -1 > ${configName}"
    sh "echo '<script>' >> ${configName}"
    sh "cat '${file}' >> ${configName}"
    sh "echo '</script>' >> ${configName}"
    sh "echo '${template}' | grep -A 100 '__PIPELINE__' | tail -n +2 >> ${configName}"
    sh "sed -i 's!__name__!${name}!g; s!__repository__!${serviceName}!g' ${configName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${projectName}/${name} < ${configName}"
    sh "rm ${configName}"
}

 

















def public cleanWorkSpace () {
    def tools = new org.mauro.Tools()
    tools.cleanWorkSpace()
}

def public publishingHTML(name, title, reportDir, files, allowMissing = false) {
    def tools = new org.mauro.Tools()
    tools.publishingHTML(name, title, reportDir, files, allowMissing)
}