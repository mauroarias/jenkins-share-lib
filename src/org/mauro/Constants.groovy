package org.mauro

public class Constants {

    private static String repoTemplate = 'https://github.com/mauroarias'
    private static String defaultAgent = 'alpine'
    private static String mavenAgent = 'maven:3.8.1-adoptopenjdk-11'
    private static String mavenTemplate = 'template-maven-app'
    private static String lacalHost = 'localhost'
    
    public static String getRepoTemplate() {
        return repoTemplate
    }

    public static String getDefaultAgent () {
        return defaultAgent
    }

    public static String getMavenAgent() {
        return mavenAgent
    }

    public static String getMavenTemplate() {
        return mavenTemplate
    }

    public static String getCommonHost() {
        return lacalHost
    }

    public static String getCommonURI() {
        return "http://${getCommonHost()}"
    }

    public static String getJenkinsHost() {
        return "${getCommonURI()}:8080"
    }

    public static String getSonarHost() {
        return "${getCommonURI()}:9000"
    }

    public static String getDockerRepository () {
        return "${getCommonHost()}:5000"
    }

    public static String getDockerRepositoryDev () {
        return getDockerRepository ()
    }

    public static String getDockerRepositoryStage () {
        return getDockerRepository ()
    }

    public static String getDockerRepositoryProd () {
        return getDockerRepository ()
    }

}