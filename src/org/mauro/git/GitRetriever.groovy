package org.mauro.git

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class GitRetriever implements Serializable {

    def static GitInterface gitInst

    def public static GitInterface configGitRep (gitDstRemote) {
        if ("${gitDstRemote}" == 'gitHub') {
            gitInst = new GitHub()
        } else if ("${gitDstRemote}" == 'bitBucket') {
            gitInst = new BitBucket()
        } else {
            error('remote not configured')
        }
        return gitInst
    }

    def public static GitInterface getGitInst (gitDstRemote) {
        return gitInst[gitDstRemote]
    }
}