package org.mauro

import org.mauro.config.Constants

class Tools {

    def public static createJenkinsPipelineFileWithLib (steps, template, version) {
        def jenkinsFile='./Jenkinsfile'
        def library=Constants.getPipelineCi()
        steps.sh "rm -f ${jenkinsFile}"
        steps.sh "echo 'building jenkins file'"
        steps.sh "echo '${template}' > ${jenkinsFile}"
        steps.sh "sed -i 's/__PIPELINE__/${library}/; s/__version__/${version}/' ${jenkinsFile}"
        return "${jenkinsFile}"
    }

    def public static publishingHTML (steps, name, title, reportDir, files, allowMissing = false) {
        steps.publishHTML([allowMissing: "${allowMissing}", 
                           alwaysLinkToLastBuild: false, 
                           keepAll: true,
                           reportDir: "${reportDir}",
                           reportFiles: "${files}",
                           reportName: "${name}",
                           reportTitles: "${title}"])
    }











    def hideTrace(cmd) {
        sh (script: '#!/bin/sh -e\n'+ cmd, returnStdout: true)
    }

    def cleanWorkSpace () {
        sh 'rm -rf * *.git*'
    }

}