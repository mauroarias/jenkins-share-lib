package org.mauro.git

def validateEnvVars () {
    if ("${GIT_HUB_USER}" == '') {   
        error("gitHub used not defined...!")
    }
    if ("${GIT_HUB_TOKEN}" == '') {   
        error("gitHub token not defined...!")
    }
}

def isRepositoryExits (projectName) {
    int status = sh(script: "curl -sLI -w '%{http_code}' -H \"Accept: application/vnd.github.v3+json\" ${getPath()}${projectName} -o /dev/null", returnStdout: true)
    echo "repository status code was ${status}"
    return status == 200
}

def getPath () {
    return "https://${GIT_HUB_USER}:${GIT_HUB_TOKEN}@github.com/${GIT_HUB_USER}/"
}

def cloneRepoWithBranch (branche, repository) {
    sh "git clone -b '${branche}' '${getPath()}${repository}'"
}

def createRepo (repository) {
    sh "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" --data '{\"name\":\"${repository}\"}' ${getApiUri()}"
}

def getApiUri () {
    return "https://api.github.com/user/repos"
}

def addRemote (repository, remote) {
    sh "git remote add ${remote} ${getPath()}${repository}.git"
}

def getRepos () {
    return sh(script: "curl -H \"Authorization: token ${GIT_HUB_TOKEN}\" -X GET ${getApiUri()} | jq -r '.[] | .name'", returnStdout: true)
}

def cloneRepo (repository) {
    sh "git clone ${getPath()}${repository}"
}

def getPathRepo(repository) {
    return "https://github.com/${GIT_HUB_USER}/${repository}"
}

def getRepoOwner() {
    return "${GIT_HUB_USER}"
}

return this