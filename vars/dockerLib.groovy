def getDockerRepositoryDev () {
    return constants.getDockerRepositoryDev()
}

def getDockerRepositoryStage () {
    def constants = new org.mauro.Constants()
    return constants.getDockerRepositoryStage()
}

def getDockerRepositoryProd () {
    def constants = new org.mauro.Constants()
    return constants.getDockerRepositoryProd()
}

def getDockerTagSufixDev () {
    return '-dev'
}

def getDockerTagSufixStage () {
    return '-stage'
}

def getDockerTagSufixProd () {
    return '-prod'
}