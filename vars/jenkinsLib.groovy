include org.mauro.Constants
include org.mauro.Tools
include org.mauro.Vault
include org.mauro.templating.Maven
include org.mauro.git.GitHub
include org.mauro.git.BitBucket

def downloadJenkinsCli () {
    sh "wget '${Constants.getJenkinsHost()}/jnlpJars/jenkins-cli.jar'"
}

def getprojects () {
    return sh(script: "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket list-jobs | grep 'PRJ-' | sed 's/PRJ-//g'", returnStdout: true)
}

def createProjectIfNotExits (projectName) {
    def template = libraryResource 'org/mauro/templates/createProject.xml'
    configFileName="./config${currentBuild.startTimeInMillis}.xml" 
    sh "echo 'creating project ${projectName}'"
    sh "echo '${template}' > ${configFileName}"
    sh "sed -i 's/<description>/<description>${projectName}/' ${configFileName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${projectName} < ${configFileName}"
    sh "rm ${configFileName}"
}








def createJenkinsMultibranchJobWithLib (gitDstRemote, repository, project, name, repoOwner, repoUrl) {
    def template = ""
    if ("${gitDstRemote}" == 'gitHub') {
        template = libraryResource 'org/mauro/templates/createMultibranchJobWithLibGitHub.xml'
    } else if ("${gitDstRemote}" == 'bitBucket') {
        template = libraryResource 'org/mauro/templates/createMultibranchJobWithLibBitBucket.xml'
    }
    configName="./config${currentBuild.startTimeInMillis}.xml"
    sh "echo 'creating multibranch job ${repository}'"
    sh "echo '${template}' > ${configName}"
    sh "sed -i 's!__name__!${name}!g; s!__repository__!${repository}!g; s!__repository_owner__!${repoOwner}!g; s!__repository_url__!${repoUrl}!g' ${configName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${project}/${repository} < ${configName}"
    sh "rm ${configName}"
}

def createJenkinsPipelineFileWithLib (library, version) {
    if ("${library}" == null || "${library}".equals('')) {
        error('new library must be defined...!')
    }
    if ("${version}" == null || "${version}".equals('')) {
        error('new version must be defined...!')
    }
    def template = libraryResource 'org/mauro/templates/JenkinsfilePipelineJobWithLibTemplate'
    jenkinsFile='./Jenkinsfile'
    sh "rm -f ${jenkinsFile}"
    sh "echo 'building jenkins file'"
    sh "echo '${template}' > ${jenkinsFile}"
    sh "sed -i 's/__PIPELINE__/${library}/; s/__version__/${version}/' ${jenkinsFile}"
    return "${jenkinsFile}"
}

def createPipelineJobWithLib (name, library, version, project, repository) {
    file = createJenkinsPipelineFileWithLib("${library}", "${version}")
    createPipelineJob("${name}", "${file}", "${project}", "${repository}")
}

def createPipelineJob (name, file, project, repository) {
    configName="./config${currentBuild.startTimeInMillis}.xml" 
    def template = libraryResource 'org/mauro/templates/createPipelineJobTemplate.xml'
    sh "echo 'creating pipeline job ${name}'"
    sh "echo '${template}' | grep -B 100 '__PIPELINE__' | head -n -1 > ${configName}"
    sh "echo '<script>' >> ${configName}"
    sh "cat '${file}' >> ${configName}"
    sh "echo '</script>' >> ${configName}"
    sh "echo '${template}' | grep -A 100 '__PIPELINE__' | tail -n +2 >> ${configName}"
    sh "sed -i 's!__name__!${name}!g; s!__repository__!${repository}!g' ${configName}"
    sh "java -jar jenkins-cli.jar -s ${Constants.getJenkinsHost()}/ -webSocket create-job PRJ-${project}/${name} < ${configName}"
    sh "rm ${configName}"
}

def cleanWorkSpace () {
    def tools = new org.mauro.Tools()
    tools.cleanWorkSpace()
}

def stash (name, includes, excludes, useDefaultExcludes = true) {
    stash name: "${name}" , includes: "${includes}" , excludes: "${excludes}" , allowEmpty: false , useDefaultExcludes: "${useDefaultExcludes}" 
}

def archivingArtifacts (artifacts) {
    archiveArtifacts(allowEmptyArchive: false, artifacts: "${artifacts}", onlyIfSuccessful: false)
}
 
def publishingHTML(name, title, reportDir, files, allowMissing = false) {
    def tools = new org.mauro.Tools()
    tools.publishingHTML(name, title, reportDir, files, allowMissing)
}