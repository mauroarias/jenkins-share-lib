def commitAndPushRepo (remote, branche, message) {
    sh "git add -A"
    sh "git commit -m \"${message}\""
    sh "git branch -M ${branche}"
    pushRepo("${remote}", "${branche}")
}

def pushRepo (remote, branche) {
    sh "git push -u ${remote} ${branche}"
}

def validateEnvVars (type) {
    sh "echo 'validating env vars in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.validateEnvVars()
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.validateEnvVars()
    }
}

def createProjectIfNotExitsIfAppl (type, projectName) {
    sh "echo 'creating project if not exists in ${type}'"
    if ("${type}" == 'gitHub') {
        sh "echo 'ignoring project abstraction, it is not supported in github'"
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.createProjectIfNotExits("${projectName}")
    }
}

def isRepositoryExits (type, repo) {
    sh "echo 'validating is repository exists in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.isRepositoryExits("${repo}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.isRepositoryExits("${repo}")
    }
}

def cloneRepoWithBranch (branche, repository) {
    sh "rm -rf ${repository}"
    sh "echo 'cloning repo in ${type}'"
    def constants = new org.mauro.Constants()
    sh "git clone -b '${branche}' '${constants.getRepoTemplate()}/${repository}'"
    return
}

def cloneRepo (repository) {
    sh "rm -rf ${repository}"
    sh "echo 'cloning repo in ${type}'"
    def constants = new org.mauro.Constants()
    sh "git clone '${constants.getRepoTemplate()}/${repository}'"
}

def createRepo (type, repository, projectName) {
    sh "echo 'creating repo in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.createRepo("${repository}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.createRepo("${repository}", "${projectName}")
    }
}

def initRepo (type, email, name, repository, remote) {
    sh "git config --global user.email \"${email}\""
    sh "git config --global user.name \"${name}\""
    sh "rm -rf .git"
    sh "git init"
    sh "echo 'adding remote in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.addRemote("${repository}", "${remote}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.addRemote("${repository}", "${remote}")
    }
}

def getRepos (type, projectName) {
    sh "echo 'getting list of repos in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        return gitHub.getRepos()
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        return bitBucket.getRepos("${projectName}")
    }
}

def getPathRepo(type, repository) {
    sh "echo 'getting repo path in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        return gitHub.getPathRepo("${repository}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        return bitBucket.getPathRepo("${repository}")
    }
}

def getRepoOwner(type) {
    sh "echo 'repository owner in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        return gitHub.getRepoOwner()
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        return bitBucket.getRepoOwner()
    }
}
