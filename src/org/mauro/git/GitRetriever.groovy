package org.mauro.git

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class GitRetriever implements Serializable {

    def static gitInst = [:]
    def static String gitDstRemote

    def public static configGitRep (gitDstRemote) {
        gitInst.add[("gitHub"): new GitHub()]
        this.gitDstRemote = gitDstRemote
        return gitInst[gitDstRemote]
    }

    def public static getGitInst (gitDstRemote) {
        return gitInst[gitDstRemote]
    }
}