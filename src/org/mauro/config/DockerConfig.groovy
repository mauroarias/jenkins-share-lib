package org.mauro.config

class DockerConfig implements Serializable {






























    

    def static configSteps
    def static configRegistryServer
    def static configRegistryRepoDev
    def static configRegistryRepoStage
    def static configRegistryRepoProd

    def public static config (stepsValue, server, repoDev, repoStage, repoProd) {
        configSteps = stepsValue
        configRegistryServer = server
        configRegistryRepoDev = repoDev
        configRegistryRepoStage = repoStage
        configRegistryRepoProd = repoProd
        printConfig()
    }

    def static printConfig () {
        configSteps.sh "echo 'configRegistryServer: ${configRegistryServer}, configRegistryRepoDev: ${configRegistryRepoDev}, configRegistryRepoStage: ${configRegistryRepoStage}, configRegistryRepoProd: ${configRegistryRepoProd}'"
    }

    def static errorIfNotConfig (value, name) {
        if ("${value}".equals('')|| "${value}" == null) {
            configSteps.error("${name} must be define...!")
        }
    }

    def public static getRegistryServer () {
        errorIfNotConfig(configRegistryServer, "Registry server")
        return configRegistryServer
    }

    def public static getRepositoryDev () {
        return "${configRegistryServer}${configRegistryRepoDev}"
    }

    def public static getRepositoryStage () {
        return "${configRegistryServer}${configRegistryRepoStage}"
    }

    def public static ggetRepositoryProd () {
        return "${configRegistryServer}${configRegistryRepoProd}"
    }
}