package org.mauro

def getMavenAgent() {
    return 'maven:3.8.1-adoptopenjdk-11'
}

def getMavenTemplate() {
    return 'template-maven-app'
}

def getCommonHost() {
    return 'http://localhost'
}

def getJenkinsHost() {
    return "${getCommonHost()}:8080"
}

def getSonarHost() {
    return "${getCommonHost()}:9000"
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