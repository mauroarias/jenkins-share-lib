package org.mauro.config

import org.mauro.templating.BuilderRetriever

class Config implements Serializable {

    def static configSteps
    def static configTemplate
    def static configTemplateName
    def static configFullName
    def static configAgent
    def static configCiBranch
    def static configCiVersion
    def static configCdType
    def static configCdVersion

    def public static configByTemplate (stepsValue, templateFile, templateFullName) {
        configSteps = stepsValue
        configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .template'", returnStdout: true).trim()
        configTemplateName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .name'", returnStdout: true).trim()
        configFullName = templateFullName
        configAgent = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .agent'", returnStdout: true).trim()
        configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .branch'", returnStdout: true).trim()
        configCiVersion = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .version'", returnStdout: true).trim()
        configCdType = getCdType()
        configCdVersion = getCdVersion()

        def configCiBranchFromManifest = getCiType()
        if (configCiBranch != configCiBranchFromManifest) {
            error("template doesn't match with manifest")
        }
        def configCiVersionFromManifest = getCiType()
        if (configCiVersion != configCiVersionFromManifest) {
            error("version doesn't match with manifest")
        }

        printConfig()
        BuilderRetriever.configBuider(stepsValue)
    }

    def public static configByManifest (stepsValue, templateFile) {
        configSteps = stepsValue
        configTemplateName = getCiType()
        configCiVersion = getCiVersion()
        configCdType = getCdType()
        configCdVersion = getCdVersion()
        configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .template'", returnStdout: true).trim()
        configFullName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .fullName'", returnStdout: true).trim()
        configAgent = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .agent'", returnStdout: true).trim()
        configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .branch'", returnStdout: true).trim()
        printConfig()
        BuilderRetriever.configBuider(stepsValue)
    }

    def static printConfig () {
        configSteps.sh "echo 'templateName: ${templateName}, configTemplateName: ${configTemplateName}, configCiVersion: ${configCiVersion}, configTemplate: ${configTemplate}, configFullName: ${configFullName}, configAgent: ${configAgent}, configCiBranch: ${configCiBranch}, configCdType: ${configCdType}, configCdVersion: ${configCdVersion}'"
    }

    def static errorIfNotConfig (value, name) {
        if ("${value}".equals('')|| "${value}" == null) {
            configSteps.error("${name} must be define...!")
        }
    }

    def static getCiType () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true).trim()
    }

    def static getCiVersion () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true).trim()
    }

    def static getCdType () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.cd.type'", returnStdout: true).trim()
    }

    def static getCdVersion () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.cd.version'", returnStdout: true).trim()
    }

    def public static getTemplate () {
        errorIfNotConfig(configTemplate, "configTemplate")
        return configTemplate
    }

    def public static getTemplateName () {
        errorIfNotConfig(configTemplateName, "configTemplate name")
        return configTemplateName
    }

    def public static getFullname () {
        errorIfNotConfig(configFullName, "full name")
        return configFullName
    }

    def public static getAgent () {
        errorIfNotConfig(configAgent, "configAgent")
        return configAgent
    }

    def public static getBranch () {
        errorIfNotConfig(configCiBranch, "branch")
        return configCiBranch
    }

    def public static getVersion () {
        errorIfNotConfig(configCiVersion, "version")
        return configCiVersion
    }

    def public static getCdType () {
        errorIfNotConfig(configCdType, "cd type")
        return configCdType
    }

    def public static getCdVersion () {
        errorIfNotConfig(configCdVersion, "cd version")
        return configCdVersion
    }
}