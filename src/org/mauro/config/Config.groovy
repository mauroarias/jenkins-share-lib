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

    def public static configByconfigTemplate (stepsValue, templateFile, templateFullName) {
        configSteps = stepsValue
        configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.configFullName == \"${templateFullName}\") | .configTemplate'", returnStdout: true)
        configTemplateName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.configFullName == \"${templateFullName}\") | .name'", returnStdout: true)
        configFullName = templateFullName
        configAgent = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.configFullName == \"${templateFullName}\") | .configAgent'", returnStdout: true)
        configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.configFullName == \"${templateFullName}\") | .branch'", returnStdout: true)
        configCiVersion = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.configFullName == \"${templateFullName}\") | .version'", returnStdout: true)
        BuilderRetriever.configBuider(stepsValue)
        printConfig()
    }

    def public static configByManifest (stepsValue, templateFile) {
        configSteps = stepsValue
        configTemplateName = getCiType()
        configCiVersion = getConfig()
        configTemplate = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .configTemplate'", returnStdout: true)
        configFullName = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .configFullName'", returnStdout: true)
        configAgent = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .configAgent'", returnStdout: true)
        configCiBranch = configSteps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${configTemplateName}\")  | select(.version == \"${configCiVersion}\") | .branch'", returnStdout: true)
        BuilderRetriever.configBuider(stepsValue)
        printConfig()
    }

    def static printConfig () {
        configSteps.sh "echo 'templateName: ${templateName}, configTemplateName: ${configTemplateName}, configCiVersion: ${configCiVersion}, configTemplate: ${configTemplate}, configFullName: ${configFullName}, configAgent: ${configAgent}, configCiBranch: ${configCiBranch}'"
    }

    def static errorIfNotConfig (value, name) {
        if ("${value}".equals('')|| "${value}" == null) {
            configSteps.error("${name} must be define...!")
        }
    }

    def static getCiType () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true)
    }

    def static getConfig () {
        return configSteps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
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
}