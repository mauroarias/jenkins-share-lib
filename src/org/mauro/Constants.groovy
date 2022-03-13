package org.mauro

def getRepoTemplate() {
    return 'https://github.com/mauroarias'
}

def getDefaultTemplate () {
    return 'alpine'
}

def getMavenAgent() {
    return 'maven:3.8.1-adoptopenjdk-11'
}

def getMavenTemplate() {
    return 'template-maven-app'
}

def getCommonHost() {
    return 'localhost'
}

def getCommonURI() {
    return "http://${getCommonHost()}"
}

def getJenkinsHost() {
    return "${getCommonURI()}:8080"
}

def getSonarHost() {
    return "${getCommonURI()}:9000"
}

def getDockerRepository () {
    return "${getCommonHost()}:5000"
}

def getDockerRepositoryDev () {
    return getDockerRepository ()
}

def getDockerRepositoryStage () {
    return getDockerRepository ()
}

def getDockerRepositoryProd () {
    return getDockerRepository ()
}

return this