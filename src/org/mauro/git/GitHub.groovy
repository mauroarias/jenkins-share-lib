package org.mauro.git

import org.mauro.Tools

class GitHub implements Serializable {

    def String getApiUri ='https://api.github.com/user/repos'

    def public createProjectIfNotExits (steps, projectName) {
        steps.sh "echo 'ignoring project abstraction, it is not supported in github'"
    }

    def public isRepositoryExits (steps, projectName) {
        def status = steps.sh(script: "curl -sLI -w '%{http_code}' -H \"Accept: application/vnd.github.v3+json\" ${getPath(steps)}${projectName} -o /dev/null", returnStdout: true)
        steps.sh "echo 'repository status code was ${status}'"
        return status == '200'
    }

    def public getPath (steps) {
        return "https://${steps.env.GIT_HUB_CRED_USR}:${steps.env.GIT_HUB_CRED_PSW}@github.com/${steps.env.GIT_HUB_CRED_USR}/"
    }

    def public cloneRepo (steps, branch, repoTemplate, template, serviceName) {
        steps.sh "git clone -b ${branch} ${repoTemplate}/${template} ./${serviceName}"
    }

    def public createRepo (steps, serviceName, projectName) {
        steps.sh "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" --data '{\"name\":\"${serviceName}\"}' ${getApiUri}"
    }

    def public initRepo (steps, serviceName) {
        steps.sh "git config --global user.email \"$GIT_EMAIL\""
        steps.sh "git config --global user.name \"$GIT_USER\""
        steps.sh "rm -rf .git"
        steps.sh "git init"
        addRemote(steps, serviceName)
    }

    def public addRemote (steps, serviceName) {
        steps.sh "git remote add origin ${getPath(steps)}${serviceName}.git"
    }

    def public commitAndPushRepo (steps) {
        def branch=Constants.getGitBranch()
        steps.sh "git add -A"
        steps.sh "git commit -m 'first draft from template'"
        steps.sh "git branch -M ${branche}"
        steps.sh "git push -u origin ${branche}"
    }











    def public getRepos () {
        return sh(script: "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" -X GET ${getApiUri} | jq -r '.[] | .name'", returnStdout: true)
    }

    def public getPathRepo(repository) {
        return "https://github.com/${GIT_HUB_USER}/${repository}"
    }

    def public getRepoOwner() {
        return "${GIT_HUB_USER}"
    }
}