package org.mauro

class Tools {

    def static scriptRunner

    def public static setRunner (scriptRunner) {
        this.scriptRunner = scriptRunner
    }

    def public static getRunner () {
        if (scriptRunner == null) {
            error ("script runner must be initilised to use the jekins lib")
        }
        return scriptRunner
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