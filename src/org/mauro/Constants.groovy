package org.mauro

final class Constants {
    private final static repoTemplate = 'https://github.com/mauroarias'
    private final static defaultAgent = 'alpine'
    private final static mavenAgent = 'maven:3.8.1-adoptopenjdk-11'
    private final static mavenTemplate = 'template-maven-app'
    private final static commonHost = 'localhost'

    static String getRepoTemplate() {
        return repoTemplate
    }

    static String getDefaultAgent () {
        return defaultAgent
    }

    static String getMavenAgent() {
        return mavenAgent
    }

    static String getMavenTemplate() {
        return mavenTemplate
    }

    static String getCommonHost() {
        return commonHost
    }

    static String getCommonURI() {
        return "http://${getCommonHost()}"
    }

    static String getJenkinsHost() {
        return "${getCommonURI()}:8080"
    }

    static String getSonarHost() {
        return "${getCommonURI()}:9000"
    }

    static String getDockerRepository () {
        return "${getCommonHost()}:5000"
    }

    static String getDockerRepositoryDev () {
        return getDockerRepository ()
    }

    static getDockerRepositoryStage () {
        return getDockerRepository ()
    }

    static String getDockerRepositoryProd () {
        return getDockerRepository ()
    }
}