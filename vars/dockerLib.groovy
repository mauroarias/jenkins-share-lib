import org.mauro.config.Constants

def public getDockerRepositoryDev () {
    return constants.getDockerRepositoryDev()
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