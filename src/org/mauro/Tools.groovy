package org.mauro

class Tools {

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