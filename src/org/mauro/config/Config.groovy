package org.mauro.config

class config implements Serializable {

    def static steps
    def static template
    def static templateName
    def static fullName
    def static agent
    def static ciBranch
    def static ciVersion

    def public static configByTemplate (stepsValue, templateFile, templateFullName) {
        steps = stepsValue
        template = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .template'", returnStdout: true)
        templateName = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .name'", returnStdout: true)
        fullName = templateFullName
        agent = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .agent'", returnStdout: true)
        ciBranch = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .branch'", returnStdout: true)
        ciVersion = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.fullName == \"${templateFullName}\") | .version'", returnStdout: true)
    }

    def public static configBymanifest (stepsValue, templateFile) {
        steps = stepsValue
        templateName = getCiType(stepsValue)
        ciVersion = getCiVersion(stepsValue)
        template = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${templateName}\")  | select(.version == \"${ciVersion}\") | .template'", returnStdout: true)
        fullName = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${templateName}\")  | select(.version == \"${ciVersion}\") | .fullName'", returnStdout: true)
        agent = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${templateName}\")  | select(.version == \"${ciVersion}\") | .agent'", returnStdout: true)
        ciBranch = steps.sh(script: "echo '${templateFile}' | yq -o=x eval '.types[] | select(.name == \"${templateName}\")  | select(.version == \"${ciVersion}\") | .branch'", returnStdout: true)
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

    def static getCiVersion (steps) {
        def ciVersion=steps.sh(script: "cat ./manifest.yaml | yq -o=x '.ci.version'", returnStdout: true)
        return "${ciVersion}"
    }

    def public static getTemplate () {
        errorIfNotConfig(template, "template")
        return template
    }

    def public static getTemplateName () {
        errorIfNotConfig(templateName, "template name")
        return templateName
    }

    def public static getFullname () {
        errorIfNotConfig(fullName, "full name")
        return fullName
    }

    def public static getAgent () {
        errorIfNotConfig(agent, "agent")
        return agent
    }

    def public static getBranch () {
        errorIfNotConfig(ciBranch, "branch")
        return ciBranch
    }

    def public static getVersion () {
        errorIfNotConfig(ciVersion, "version")
        return ciVersion
    }
}