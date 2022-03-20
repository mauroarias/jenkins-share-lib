package org.mauro.git

import org.mauro.Tools

class GitHub implements Serializable {

    def static String getApiUri ='https://api.github.com/user/repos'

    def public static createProjectIfNotExits (steps, projectName) {
        steps.sh "echo 'ignoring project abstraction, it is not supported in github'"
    }

    def public static isRepositoryExits (steps, projectName) {
        int status = steps.sh(script: "curl -sLI -w '%{http_code}' -H \"Accept: application/vnd.github.v3+json\" ${getPath(steps)}${projectName} -o /dev/null", returnStdout: true)
        echo "repository status code was ${status}"
        return status == 200
    }

    def public static getPath (steps) {
        return "https://${GIT_HUB_CRED_USR}:${GIT_HUB_CRED_PSW}@github.com/${GIT_HUB_CRED_USR}/"
    }

    def public static cloneRepo (steps, branche, repoTemplate, template, serviceName) {
        steps.sh "git clone -b ${branch} ${repoTemplate}/${template} ./${serviceName}"
    }

    def public static createRepo (steps, serviceName, projectName) {
        steps.sh "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" --data '{\"name\":\"${serviceName}\"}' ${getApiUri}"
    }

    def public static initRepo (steps, serviceName) {
        sh "git config --global user.email \"$GIT_EMAIL\""
        sh "git config --global user.name \"$GIT_USER\""
        sh "rm -rf .git"
        sh "git init"
        addRemote(steps, serviceName)
    }

    def public static addRemote (steps, serviceName) {
        steps.sh "git remote add origin ${getPath(steps)}${serviceName}.git"
    }

    def public static commitAndPushRepo (steps) {
        def branch=Constants.getGitBranch()
        steps.sh "git add -A"
        steps.sh "git commit -m 'first draft from template'"
        steps.sh "git branch -M ${branche}"
        steps.sh "git push -u origin ${branche}"
    }











    def public static getRepos () {
        return sh(script: "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" -X GET ${getApiUri} | jq -r '.[] | .name'", returnStdout: true)
    }

    def public static getPathRepo(repository) {
        return "https://github.com/${GIT_HUB_USER}/${repository}"
    }

    def public static getRepoOwner() {
        return "${GIT_HUB_USER}"
    }
}