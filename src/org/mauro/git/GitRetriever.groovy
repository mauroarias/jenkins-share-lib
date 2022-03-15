package org.mauro.git

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class GitRetriever implements Serializable {

    def static GitInterface gitInst

    def public static GitInterface configGitRep (remote) {
        if ("${remote}" == 'gitHub') {
            gitInst = new GitHub()
        } else if ("${remote}" == 'bitBucket') {
            gitInst = new BitBucket()
        } else {
            error('remote not configured')
        }
        return gitInst
    }

    def public static GitInterface getGitInst () {
        if (gitInst == null) {
            error('remote not configured')
        }
        return gitInst
    }
}