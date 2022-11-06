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

    def public build (app) {
        steps.sh 'mvn clean package'
        Tools.stash(steps, Constants.getStashName(), "target/**/*", '')
        Tools.archivingArtifacts(steps, "target/${app}.jar")
        Tools.publishingHTML(steps, 'code coverage', 'code coverage report', 'target/jacoco-report/', 'index.html', true)
    }

    def public pushSonarAnalysis (artifactId) {
        steps.sh "mvn clean verify sonar:sonar -Dsonar.projectKey=${artifactId} -Dsonar.host.url='${Constants.getSonarHost()}' -Dsonar.login=${steps.env.SONAR_TOKEN} -Dsonar.projectName=${artifactId}"
    }

    def public runMutationTests () {
        steps.sh 'mvn package -Pmutation -DtimestampedReports=false'
        Tools.publishingHTML(steps, 'mutation test', 'mutation test report', "target/pit-reports/", 'index.html', true)
    }

    def public runDependencyCheck () {
        steps.sh 'mvn verify -Powasp'
        Tools.publishingHTML(steps, 'dependency check', 'dependency check report', "target/owasp-report", 'dependency-check-report.html', true)
    }
}