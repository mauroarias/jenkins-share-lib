package org.mauro.git

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class GitRetriever implements Serializable {

    def static gitInst

    def public static configGitRep (gitDstRemote) {
        if ("${remote}" == 'gitHub') {
            gitInst = new GitHub()
        } else if ("${remote}" == 'bitBucket') {
            gitInst = new BitBucket()
        } else {
            error('remote not configured')
        }
        return gitInst
    }

    def public static getGitInst (gitDstRemote) {
        return gitInst[gitDstRemote]
    }
}