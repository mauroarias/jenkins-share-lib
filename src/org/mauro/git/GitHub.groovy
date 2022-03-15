package org.mauro.git

class GitHub implements Serializable, GitInterface {

    def String getApiUri ='https://api.github.com/user/repos'

    def createProjectIfNotExits (projectName) {
        sh "echo 'ignoring project abstraction, it is not supported in github'"
    }

    def isRepositoryExits (projectName) {
        int status = sh(script: "curl -sLI -w '%{http_code}' -H \"Accept: application/vnd.github.v3+json\" ${getPath()}${projectName} -o /dev/null", returnStdout: true)
        echo "repository status code was ${status}"
        return status == 200
    }

    def getPath () {
        return "https://${gitHubUser}:${gitHubPassword}@github.com/${gitHubUser}/"
    }










    def createRepo (repository) {
        sh "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" --data '{\"name\":\"${repository}\"}' ${getApiUri}"
    }

    def addRemote (repository, remote) {
        sh "git remote add ${remote} ${getPath()}${repository}.git"
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