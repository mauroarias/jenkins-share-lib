package org.mauro.git

class BitBucket implements Serializable {

    def String baseApiPath = 'https://api.bitbucket.org/2.0/'

    def createProjectIfNotExits (steps, projectName) {
        steps.sh "echo 'checking project name: ${projectName}'"
        projectNameKey = "${projectName}".toString().toUpperCase()
        if (!isProjectExits(steps, projectName)) {
            sh "curl --user '${getAuth()}' -X POST -H \"Content-Type: application/json\" ${getProjectApiPath()} --data '{\"key\":\"${projectNameKey}\",\"name\":\"${projectName}\",\"description\":\"${projectName}\",\"is_private\": false}'"
        }
    }

    def getAuth () {
        return "${BIT_BUCKET_CRED_USR}:${BIT_BUCKET_CRED_PSW}"
    }

    def getProjectApiPath () {
        return "${baseApiPath}workspaces/${BIT_BUCKET_CRED_USR}/projects/"
    }

    def isProjectExits (steps, projectName) {
        int status = steps.sh(script: "curl -sLI -w '%{http_code}' --user ${getAuth()} -X GET -H \"Content-Type: application/json\" ${getProjectApiPath()}${projectName} -o /dev/null", returnStdout: true)
        echo "project status code was ${status}"
        return status == 200
    }

    def isRepositoryExits (steps, repo) {
        int status = steps.sh(script: "curl -sLI -w '%{http_code}' --user '${getAuth()}' -X GET -H \"Content-Type: application/json\" ${getRepositoryApiPath()}${repo} -o /dev/null", returnStdout: true)
        echo "repository status code was ${status}"
        return status == 200
    }

    def getRepositoryApiPath () {
        return "${baseApiPath}repositories/${BIT_BUCKET_CRED_USR}/"
    }

    def cloneRepo (steps, branche, repoTemplate, template, serviceName) {
        steps.sh "git clone -b ${branch} ${repoTemplate}/${template} ./${serviceName}"
    }

    def createRepo (steps, serviceName, projectName) {
        projectNameKey = "${projectName}".toString().toUpperCase()
        steps.sh "curl --user ${getAuth()} -X POST -H \"Content-Type: application/json\" --data '{\"scm\":\"git\",\"project\":{\"key\":\"${projectNameKey}\"}}' ${getRepositoryApiPath()}${serviceName}"
    }

    def public initRepo (steps, serviceName) {
        sh "git config --global user.email \"$GIT_EMAIL\""
        sh "git config --global user.name \"$GIT_USER\""
        sh "rm -rf .git"
        sh "git init"
        addRemote(steps, serviceName)
    }

    def addRemote (steps, serviceName) {
        steps.sh "git remote add origin ${getPath()}${serviceName}.git"
    }

    def getPath () {
        return "https://${getAuth()}@bitbucket.org/${BIT_BUCKET_CRED_USR}/"
    }

    def public commitAndPushRepo (steps) {
        def branch=Constants.getGitBranch()
        steps.sh "git add -A"
        steps.sh "git commit -m 'first draft from template'"
        steps.sh "git branch -M ${branche}"
        steps.sh "git push -u origin ${branche}"
    }


















    def getRepos (projectName) {
        projectNameKey = "${projectName}".toString().toUpperCase()
        return sh(script: "curl --user ${getAuth()} -X GET --url '${getRepositoryApiPath()}?q=project.key%3D+%22${projectNameKey}%22&pagelen=100' --header 'Accept: application/json' | jq -r '.values[] | .name'", returnStdout: true)
    }

    def getPathRepo(repository) {
        return "https://bitbucket.org/${BIT_BUCKET_CRED_USR}/${repository}"
    }

    def getRepoOwner() {
        return "${BIT_BUCKET_CRED_USR}"
    }
}