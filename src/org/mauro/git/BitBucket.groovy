package org.mauro.git

class BitBucket implements Serializable {
    def validateEnvVars () {
        if ("${BITBUCKET_PASSWD}" == '') {   
            error("gitHub password not defined...!")
        }
        if ("${BITBUCKET_USER}" == '') {   
            error("gitHub user must be not defined...!")
        }
    }

    def createProjectIfNotExits (projectName) {
        sh "echo 'checking project name: ${projectName}'"
        projectNameKey = "${projectName}".toString().toUpperCase()
        if (!isProjectExits("${projectName}")) {
            sh "curl --user ${getAuth()} -X POST -H \"Content-Type: application/json\" ${getProjectApiPath()} --data '{\"key\":\"${projectNameKey}\",\"name\":\"${projectName}\",\"description\":\"${projectName}\",\"is_private\": false}'"
        }
    }

    def getAuth () {
        return "${BITBUCKET_USER}:${BITBUCKET_PASSWD}"
    }

    def getProjectApiPath () {
        return "${getBaseApiPath()}workspaces/${BITBUCKET_USER}/projects/"
    }

    def getBaseApiPath () {
        return "https://api.bitbucket.org/2.0/"
    }

    def isProjectExits (projectName) {
        int status = sh(script: "curl -sLI -w '%{http_code}' --user ${getAuth()} -X GET -H \"Content-Type: application/json\" ${getProjectApiPath()}${projectName} -o /dev/null", returnStdout: true)
        echo "project status code was ${status}"
        return status == 200
    }

    def isRepositoryExits (repo) {
        int status = sh(script: "curl -sLI -w '%{http_code}' --user ${getAuth()} -X GET -H \"Content-Type: application/json\" ${getRepositoryApiPath()}${repo} -o /dev/null", returnStdout: true)
        echo "repository status code was ${status}"
        return status == 200
    }

    def getRepositoryApiPath () {
        return "${getBaseApiPath()}repositories/${BITBUCKET_USER}/"
    }

    def getPath () {
        return "https://${getAuth()}@bitbucket.org/${BITBUCKET_USER}/"
    }

    def createRepo (repository, projectName) {
        projectNameKey = "${projectName}".toString().toUpperCase()
        sh "curl --user ${getAuth()} -X POST -H \"Content-Type: application/json\" --data '{\"scm\":\"git\",\"project\":{\"key\":\"${projectNameKey}\"}}' ${getRepositoryApiPath()}${repository}"
    }

    def addRemote (repository, remote) {
        sh "git remote add ${remote} ${getPath()}${repository}.git"
    }

    def getRepos (projectName) {
        projectNameKey = "${projectName}".toString().toUpperCase()
        return sh(script: "curl --user ${getAuth()} -X GET --url '${getRepositoryApiPath()}?q=project.key%3D+%22${projectNameKey}%22&pagelen=100' --header 'Accept: application/json' | jq -r '.values[] | .name'", returnStdout: true)
    }

    def getPathRepo(repository) {
        return "https://bitbucket.org/${BITBUCKET_USER}/${repository}"
    }

    def getRepoOwner() {
        return "${BITBUCKET_USER}"
    }
}