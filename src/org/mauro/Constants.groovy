package org.mauro

class Constants implements Serializable {

    def private static String repoTemplate = 'https://github.com/mauroarias'
    def private static String defaultAgent = 'alpine'
    def private static String mavenAgent = 'maven:3.8.1-adoptopenjdk-11'
    def private static String mavenTemplate = 'template-maven-app'
    def private static String lacalHost = 'localhost'
    
    def public static String getRepoTemplate() {
        return repoTemplate
    }

    def public static String getDefaultAgent () {
        return defaultAgent
    }

    def public static String getMavenAgent() {
        return mavenAgent
    }

    def public static String getMavenTemplate() {
        return mavenTemplate
    }

    def public static String getCommonHost() {
        return lacalHost
    }

    def public static String getCommonURI() {
        return "http://${getCommonHost()}"
    }

    def public static String getJenkinsHost() {
        return "${getCommonURI()}:8080"
    }

    def public static String getSonarHost() {
        return "${getCommonURI()}:9000"
    }

    def public static String getDockerRepository () {
        return "${getCommonHost()}:5000"
    }

    def public static String getDockerRepositoryDev () {
        return getDockerRepository ()
    }

    def public static String getDockerRepositoryStage () {
        return getDockerRepository ()
    }

    def public static String getDockerRepositoryProd () {
        return getDockerRepository ()
    }

}