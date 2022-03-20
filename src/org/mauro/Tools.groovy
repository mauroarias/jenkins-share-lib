package org.mauro

class Tools {

    def public static createJenkinsPipelineFileWithLib (steps, library, version) {
        def template = libraryResource 'org/mauro/templates/JenkinsfilePipelineJobWithLibTemplate'
        def jenkinsFile='./Jenkinsfile'
        steps.sh "rm -f ${jenkinsFile}"
        steps.sh "echo 'building jenkins file'"
        steps.sh "echo '${template}' > ${jenkinsFile}"
        steps.sh "sed -i 's/__PIPELINE__/${library}/; s/__version__/${version}/' ${jenkinsFile}"
        return "${jenkinsFile}"
    }

    def hideTrace(cmd) {
        sh (script: '#!/bin/sh -e\n'+ cmd, returnStdout: true)
    }

    def cleanWorkSpace () {
        sh 'rm -rf * *.git*'
    }

    def publishingHTML (name, title, reportDir, files, allowMissing = false) {
        publishHTML([allowMissing: "${allowMissing}", 
                     alwaysLinkToLastBuild: false, 
                     keepAll: true,
                     reportDir: "${reportDir}",
                     reportFiles: "${files}",
                     reportName: "${name}",
                     reportTitles: "${title}"])
    }
}