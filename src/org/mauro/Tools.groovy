package org.mauro

import org.mauro.config.Constants

class Tools {

    def public static createJenkinsPipelineFileWithLib (steps, pipelineFile, pipelineVersion, templateName) {
        def jenkinsLocalFile='./Jenkinsfile'
        steps.sh "rm -f ${jenkinsLocalFile}"
        steps.sh "echo 'building jenkins file'"
        steps.sh "echo '${pipelineFile}' > ${jenkinsLocalFile}"
        steps.sh "sed -i 's/__PIPELINE__/${templateName}/; s/__version__/${pipelineVersion}/' ${jenkinsLocalFile}"
        return "${jenkinsLocalFile}"
    }



















    


    // def public static publishingHTML (steps, name, title, reportDir, files, allowMissing = false) {
    //     steps.publishHTML([allowMissing: "${allowMissing}", 
    //                        alwaysLinkToLastBuild: false, 
    //                        keepAll: true,
    //                        reportDir: "${reportDir}",
    //                        reportFiles: "${files}",
    //                        reportName: "${name}",
    //                        reportTitles: "${title}"])
    // }

    // def public static stash (steps, name, imports, excludes, useDefaultExcludes = true) {
    //     steps.stash name: "${name}" , imports: "${imports}" , excludes: "${excludes}" , allowEmpty: false , useDefaultExcludes: "${useDefaultExcludes}" 
    // }

    // def public static archivingArtifacts (steps, artifacts) {
    //     steps.archiveArtifacts(allowEmptyArchive: false, artifacts: "${artifacts}", onlyIfSuccessful: false)
    // }











    // def hideTrace(cmd) {
    //     sh (script: '#!/bin/sh -e\n'+ cmd, returnStdout: true)
    // }

    // def cleanWorkSpace () {
    //     sh 'rm -rf * *.git*'
    // }

}