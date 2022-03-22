package org.mauro.config

import org.mauro.templating.BuilderRetriever

class Config implements Serializable {

    def static steps
    def static template
    def static templateName
    def static fullName
    def static agent
    def static ciBranch
    def static ciVersion

    def public static configByTemplate (stepsValue, templateFile, templateFullName) {
        Config.steps = stepsValue
        Config.template = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .template'", returnStdout: true)
        Config.templateName = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .name'", returnStdout: true)
        Config.fullName = templateFullName
        Config.agent = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .agent'", returnStdout: true)
        Config.ciBranch = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .branch'", returnStdout: true)
        Config.ciVersion = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .version'", returnStdout: true)
        BuilderRetriever.configBuider(stepsValue)
    }

    def public static configByManifest (stepsValue, templateFile) {
        Config.steps = stepsValue
        Config.templateName = getCiType(stepsValue)
        Config.ciVersion = getConfig(stepsValue)
        Config.template = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${Config.templateName}\")  | select(.version == \"${Config.ciVersion}\") | .template'", returnStdout: true)
        Config.fullName = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${Config.templateName}\")  | select(.version == \"${Config.ciVersion}\") | .fullName'", returnStdout: true)
        Config.agent = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${Config.templateName}\")  | select(.version == \"${Config.ciVersion}\") | .agent'", returnStdout: true)
        Config.ciBranch = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${Config.templateName}\")  | select(.version == \"${Config.ciVersion}\") | .branch'", returnStdout: true)
        BuilderRetriever.configBuider(stepsValue)
    }

    def static errorIfNotConfig (value, name) {
        if ("${value}".equals('')|| "${value}" == null) {
            steps.error("${name} must be define...!")
        }
    }

    def static getCiType (steps) {
        def ciLibrary = steps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.type'", returnStdout: true)
        return "${ciLibrary}"
    }

    def static getConfig (steps) {
        def ciVersion=steps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
        return "${ciVersion}"
    }

    def public static getTemplate () {
        errorIfNotConfig(Config.template, "template")
        return Config.template
    }

    def public static getTemplateName () {
        errorIfNotConfig(Config.templateName, "template name")
        return Config.templateName
    }

    def public static getFullname () {
        errorIfNotConfig(Config.fullName, "full name")
        return Config.fullName
    }

    def public static getAgent () {
        errorIfNotConfig(Config.agent, "agent")
        return Config.agent
    }

    def public static getBranch () {
        errorIfNotConfig(Config.ciBranch, "branch")
        return Config.ciBranch
    }

    def public static getVersion () {
        errorIfNotConfig(Config.ciVersion, "version")
        return Config.ciVersion
    }
}