package org.mauro.git

interface GitInterface {





    def validateEnvVars ()

    def isRepositoryExits (projectName)

    def getPath ()

    def createRepo (repository)

    def getApiUri ()

    def addRemote (repository, remote)

    def getRepos ()

    def getPathRepo(repository)

    def getRepoOwner()
}