package org.mauro.git

interface GitInterface {

    def public createProjectIfNotExits(projectName)

    def public isRepositoryExits (projectName)





    // def public createRepo (repository)


    // // def addRemote (repository, remote) {
    // //     sh "git remote add ${remote} ${getPath()}${repository}.git"
    // // }

    // def public getRepos ()

    // // def getPathRepo(repository) {
    // //     return "https://github.com/${GIT_HUB_USER}/${repository}"
    // // }

    // // def getRepoOwner() {
    // //     return "${GIT_HUB_USER}"
    // // }
}