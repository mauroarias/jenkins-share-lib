import org.mauro.config.Constants
import org.mauro.git.GitRetriever

def public getGitDstRepos () {
    def templateTypeList = libraryResource 'org/mauro/templates/templates.yaml'
    return sh(script: "echo 'gitHub' && echo 'bitBucket'", returnStdout: true)
}

def public getRepos (gitDstRemote, projectName) {
    GitRetriever.configGitRep(gitDstRemote)
    return GitRetriever.getGitInst().getRepos(this, projectName)
}










def public cloneRepo (serviceName) {
    sh "rm -rf ${serviceName}"
    sh "git clone '${Constants.getRepoTemplate()}/${serviceName}'"
}























def public configGitRep (remote) {
    GitRetriever.configGitRep(remote)
}








def public createProjectIfNotExitsIfAppl (steps, projectName) {
    sh "echo 'creating project ${projectName} if not exists'"
    GitRetriever.getGitInst().createProjectIfNotExits(steps, projectName)
}

def public isRepositoryExits (repo) {
    sh "echo 'validating is repository exists in ${type}'"
    GitRetriever.getGitInst().isRepositoryExits(repo)
}
















def public commitAndPushRepo (remote, branch, message) {
    sh "git add -A"
    sh "git commit -m \"${message}\""
    sh "git branch -M ${branch}"
    pushRepo("${remote}", "${branch}")
}

def public pushRepo (remote, branch) {
    sh "git push -u ${remote} ${branch}"
}

def public validateEnvVars (type) {
    sh "echo 'validating env vars in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.validateEnvVars()
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.validateEnvVars()
    }
}

def public cloneRepoWithBranch (branch, repository) {
    sh "rm -rf ${repository}"
    sh "git clone -b '${branch}' '${Constants.getRepoTemplate()}/${repository}'"
    return
}

def public createRepo (type, repository, projectName) {
    sh "echo 'creating repo in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        gitHub.createRepo("${repository}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        bitBucket.createRepo("${repository}", "${projectName}")
    }
}

def public initRepo (type, email, name, repository, remote) {
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

def public getPathRepo(type, repository) {
    sh "echo 'getting repo path in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        return gitHub.getPathRepo("${repository}")
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        return bitBucket.getPathRepo("${repository}")
    }
}

def public getRepoOwner(type) {
    sh "echo 'repository owner in ${type}'"
    if ("${type}" == 'gitHub') {
        def gitHub = new org.mauro.git.GitHub()
        return gitHub.getRepoOwner()
    } else if ("${type}" == 'bitBucket') {
        def bitBucket = new org.mauro.git.BitBucket()
        return bitBucket.getRepoOwner()
    }
}
