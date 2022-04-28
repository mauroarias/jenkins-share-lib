import org.mauro.config.Constants

def public getDockerRepositoryDev () {
    return Constants.getDockerRepositoryDev()
}

def public buildDockerImage (image) {
    unstash Constants.getStashName()
    sh "docker build -t ${getDockerRepositoryDev()}/${image} ." 
}

def public pushDockerImageDev (image) {
    sh "docker push ${getDockerRepositoryDev()}/${image}"
    sh "docker image tag ${getDockerRepositoryDev()}/${image} ${getDockerRepositoryDev()}/${image}-dev"
}















def public getDockerRepositoryStage () {
    return Constants.getDockerRepositoryStage()
}

def public getDockerRepositoryProd () {
    return Constants.getDockerRepositoryProd()
}

def public getDockerTagSufixDev () {
    return '-dev'
}

def public getDockerTagSufixStage () {
    return '-stage'
}

def public getDockerTagSufixProd () {
    return '-prod'
}