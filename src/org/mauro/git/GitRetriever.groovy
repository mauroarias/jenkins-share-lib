package org.mauro.git

class GitRetriever implements Serializable {

    def static gitInst

    def public static configGitRep (gitDstRemote) {
        if ("${gitDstRemote}" == 'gitHub') {
            gitInst = GitHub
        } else if ("${gitDstRemote}" == 'bitBucket') {
            gitInst = BitBucket
        } else {
            error('remote not configured')
        }
        return gitInst
    }

    def public static getGitInst (gitDstRemote) {
        if (gitInst == null) {
            error('remote not configured')
        }
        return gitInst
    }
}