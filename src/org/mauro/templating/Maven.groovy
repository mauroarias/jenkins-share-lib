package org.mauro.templating

import org.mauro.config.Constants
import org.mauro.Tools

class Maven implements Serializable {

    def steps
    def appVersion
    def appServiceName
    def artifactId
    def groupId

    def public setSteps(steps) {
        this.steps = steps
    }

    def public getAppVersion () {
        if (appVersion == null) {
            appVersion = steps.sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
        }
        return appVersion
    }

    def public getAppServiceName () {
        if (appServiceName == null) {
            appServiceName = steps.sh(script: 'mvn help:evaluate -Dexpression=project.name -q -DforceStdout', returnStdout: true).trim()
        }
        return appServiceName
    }

    def public getArtifactId () {
        if (artifactId == null) {
            artifactId = steps.sh(script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout', returnStdout: true).trim()
        }
        return artifactId
    }

    def public getgroupId () {
        if (groupId == null) {
            groupId = steps.sh(script: 'mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout', returnStdout: true).trim()
        }
        return groupId
    }

    def public build () {
        steps.sh 'mvn clean package'
    }

    def public getOutFolder () {
        return 'target/'
    }

    def public publishTestCoverageReport () {
        Tools.publishingHTML(steps, 'code coverage', 'code coverage report', 'target/jacoco-report/', 'index.html', true)
    }

    def public pushSonarArtifact (projectName, artifactId) {
        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${projectName} -Dsonar.host.url=${Constants.getSonarHost()} -Dsonar.login=${steps.env.SONAR_TOKEN} -Dsonar.projectName=${artifactId}"
    }
}