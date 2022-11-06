package org.mauro.config

class Constants implements Serializable {

    def public static String getCommonHost() {
        return 'localhost'
    }

    def public static String getCommonURI() {
        return "http://${getCommonHost()}"
    }

    def public static String getJenkinsHost() {
        return "${getCommonURI()}:8080"
    }

    def public static String getRepoTemplate () {
        return 'https://github.com/mauroarias'
    }

    def public static String getGitBranch () {
        return 'develop'
    }
















    


    // def public static String getDefaultAgent () {
    //     return 'alpine'
    // }

    // def public static String getPipelineCi () {
    //     return 'pipelineCi'
    // }

    // def public static String getPipelineCd () {
    //     return 'pipelineCd'
    // }

    // def public static String getSonarHost () {
    //     return 'http://sonarqube:9000'
    // }


    // def public static String getStashName () {
    //     return 'codeBuilt'
    // }











    // def public static String getMavenAgent() {
    //     return 'maven:3.8.1-adoptopenjdk-11'
    // }

    // def public static String getMavenTemplate() {
    //     return 'template-maven-app'
    // }

}