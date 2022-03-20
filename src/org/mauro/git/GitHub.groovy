package org.mauro.git

import org.mauro.Tools

class GitHub implements Serializable {

    def String getApiUri ='https://api.github.com/user/repos'

    def createProjectIfNotExits (steps, projectName) {
        steps.sh "echo 'ignoring project abstraction, it is not supported in github'"
    }

    def isRepositoryExits (steps, projectName) {
        int status = steps.sh(script: "curl -sLI -w '%{http_code}' -H \"Accept: application/vnd.github.v3+json\" ${getPath(steps)}${projectName} -o /dev/null", returnStdout: true)
        echo "repository status code was ${status}"
        return status == 200
    }

    def getPath (steps) {
        return "https://${steps.ENV.GIT_HUB_CRED_USR}:${steps.ENV.GIT_HUB_CRED_PSW}@github.com/${steps.ENV.GIT_HUB_CRED_USR}/"
    }

    def cloneRepo (steps, branche, repoTemplate, template, serviceName) {
        steps.sh "git clone -b ${branch} ${repoTemplate}/${template} ./${serviceName}"
    }

    def createRepo (steps, serviceName, projectName) {
        steps.sh "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" --data '{\"name\":\"${serviceName}\"}' ${getApiUri}"
    }

    def public initRepo (steps, serviceName) {
        sh "git config --global user.email \"$GIT_EMAIL\""
        sh "git config --global user.name \"$GIT_USER\""
        sh "rm -rf .git"
        sh "git init"
        addRemote(steps, serviceName)
    }

    def addRemote (steps, serviceName) {
        steps.sh "git remote add origin ${getPath(steps)}${serviceName}.git"
    }

    def public commitAndPushRepo (steps) {
        def branch=Constants.getGitBranch()
        steps.sh "git add -A"
        steps.sh "git commit -m 'first draft from template'"
        steps.sh "git branch -M ${branche}"
        steps.sh "git push -u origin ${branche}"
    }











    def getRepos () {
        return sh(script: "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" -X GET ${getApiUri} | jq -r '.[] | .name'", returnStdout: true)
    }

    def getPathRepo(repository) {
        return "https://github.com/${GIT_HUB_USER}/${repository}"
    }

    def getRepoOwner() {
        return "${GIT_HUB_USER}"
    }
}