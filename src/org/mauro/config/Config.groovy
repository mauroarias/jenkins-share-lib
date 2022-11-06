package org.mauro.config

import org.mauro.templating.BuilderRetriever

class Config implements Serializable {

    def static configSteps
    def static configTemplate
    def static configTemplateName
    def static configCategory
    def static configFullName
    def static configCiBranch
    def static configLibVersion
    def static configTemplateVersion
    def static configPipelineVersion


    // def static configCdType
    // def static configCdVersion
    def static jenkinsCliDir

    def public static configByTemplate (stepsValue, templateFile, templateFullName) {
        configSteps = stepsValue
        configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .template'", returnStdout: true).trim()
        configTemplateName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .name'", returnStdout: true).trim()
        configCategory = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .category'", returnStdout: true).trim()
        configFullName = templateFullName
        configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .branch'", returnStdout: true).trim()
        configLibVersion = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .libVersion'", returnStdout: true).trim()
        configTemplateVersion = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .templateVersion'", returnStdout: true).trim()
        configPipelineVersion = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .pipelineVersion'", returnStdout: true).trim()
        // configCdType = getCdType()
        // configCdVersion = getCdVersion()
        printConfig()
    }

    def static printConfig () {
        configSteps.sh "echo 'configCategory: ${configCategory}, configTemplateName: ${configTemplateName}, configLibVersion: ${configLibVersion}, configTemplateVersion: ${configTemplateVersion}, configPipelineVersion: ${configPipelineVersion}, configTemplate: ${configTemplate}, configFullName: ${configFullName}, configCiBranch: ${configCiBranch}'" //, configCdType: ${configCdType}, configCdVersion: ${configCdVersion}'"
    }

    def public static setJenkinsCliDir (cliDir) {
        jenkinsCliDir = cliDir
    }

    def public static getJenkinsCliDir () {
        return jenkinsCliDir
    }

    def public static getBranch () {
        errorIfNotConfig(configCiBranch, "branch")
        return configCiBranch
    }

    def static errorIfNotConfig (value, name) {
        if ("${value}".equals('')|| "${value}" == null) {
            configSteps.error("${name} must be define...!")
        }
    }

    def public static getTemplate () {
        errorIfNotConfig(configTemplate, "configTemplate")
        return configTemplate
    }

    def public static getLibVersion () {
        errorIfNotConfig(configLibVersion, "libVersion")
        return configLibVersion
    }

    def public static getTemplateVersion () {
        errorIfNotConfig(configTemplateVersion, "templateVersion")
        return configTemplateVersion
    }

    def public static getPipelineVersion () {
        errorIfNotConfig(configPipelineVersion, "pipelineVersion")
        return configPipelineVersion
    }




















    

    // def public static configByManifest (stepsValue, templateFile) {
    //     configSteps = stepsValue
    //     configTemplateName = getCiType()
    //     configCiVersion = getCiVersion()
    //     configCdType = getCdType()
    //     configCdVersion = getCdVersion()
    //     configCategory = getCiCategory()
    //     configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\") | select(.version == \"${configCiVersion}\") | select(.category == \"${configCategory}\") | .template'", returnStdout: true).trim()
    //     configFullName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\") | select(.version == \"${configCiVersion}\") | select(.category == \"${configCategory}\") | .fullName'", returnStdout: true).trim()
    //     configAgent = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\") | select(.version == \"${configCiVersion}\") | select(.category == \"${configCategory}\") | .agent'", returnStdout: true).trim()
    //     configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\") | select(.version == \"${configCiVersion}\") | select(.category == \"${configCategory}\") | .branch'", returnStdout: true).trim()
    //     printConfig()
    //     BuilderRetriever.configBuider(stepsValue)
    // }

    // def static getCiType () {
    //     return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true).trim()
    // }

    // def static getCiVersion () {
    //     return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true).trim()
    // }

    // def static getCiCategory () {
    //     return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.category'", returnStdout: true).trim()
    // }

    // def static getCdType () {
    //     return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.cd.type'", returnStdout: true).trim()
    // }

    // def static getCdVersion () {
    //     return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.cd.version'", returnStdout: true).trim()
    // }

    // def public static getTemplateName () {
    //     errorIfNotConfig(configTemplateName, "configTemplate name")
    //     return configTemplateName
    // }

    // def public static getFullname () {
    //     errorIfNotConfig(configFullName, "full name")
    //     return configFullName
    // }

    // def public static getCategoty () {
    //     errorIfNotConfig(configCategory, "category")
    //     return configCategory
    // }

    // def public static getAgent () {
    //     errorIfNotConfig(configAgent, "configAgent")
    //     return configAgent
    // }

    // def public static getDeploymentType () {
    //     errorIfNotConfig(configCdType, "cd type")
    //     return configCdType
    // }

    // def public static getDeploymentVersion () {
    //     errorIfNotConfig(configCdVersion, "cd version")
    //     return configCdVersion
    // }
}