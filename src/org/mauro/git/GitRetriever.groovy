package org.mauro.git

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class GitRetriever implements Serializable {

    def static gitHubInst = new GitHub()
    def static bitBucketInst = new BitBucket()
    def static gitInst = [gitHub: "${gitHubInst}", bitBucket: "${bitBucketInst}"]
    def static String gitDstRemote

    def public static configGitRep (gitDstRemote) {
        this.gitDstRemote = gitDstRemote
        return gitInst[gitDstRemote]
    }

    def public static getGitInst (gitDstRemote) {
        return gitInst[gitDstRemote]
    }
}