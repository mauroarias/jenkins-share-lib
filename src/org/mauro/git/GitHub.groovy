package org.mauro.git

def getApiUri ='https://api.github.com/user/repos'

def validateEnvVars () {
    withCredentials([
        usernamePassword(credentialsId: 'github-credentials',
          usernameVariable: 'username',
          passwordVariable: 'password')
      ]) {
        if ("${username}" == '') {   
            error("gitHub used not defined...!")
        }
        if ("${password}" == '') {   
            error("gitHub token not defined...!")
        }
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

return this