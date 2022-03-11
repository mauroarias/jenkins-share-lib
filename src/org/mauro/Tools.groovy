package org.mauro

def hideTrace(cmd) {
    sh (script: '#!/bin/sh -e\n'+ cmd, returnStdout: true)
}

def cleanWorkSpace () {
    sh 'rm -rf * *.git*'
}

return this