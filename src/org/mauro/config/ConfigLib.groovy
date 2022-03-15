package org.mauro.config

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class ConfigLib implements Serializable {

    def GitHub githubInst = new GitHub()
    def BitBucket bitBucketInst = new BitBucket()
    def GitInterface gitInst
    def static ConfigLib configLibInst

    def public static ConfigLib getConfig () {
        if (configLibInst == null) {
            configLibInst = new ConfigLib()
        }
        return configLibInst
    }

    def public GitInterface getGitRemoteHandler (remote) {
        if ("${remote}" == 'gitHub') {
            return githubInst
        } else if ("${remote}" == 'bitBucket') {
            return bitBucketInst
        } else {
            sh "echo 'remote not supported'"
        }
    }

    def public void configGitRep (remote) {
        if ("${remote}" == 'gitHub') {
            gitInst = githubInst
        } else if ("${remote}" == 'bitBucket') {
            gitInst = bitBucketInst
        } else {
            sh "echo 'remote not supported'"
        }
    }

    def public GitInterface getGitInst () {
        if (gitInst == null) {
            error('remote not configured')
        }
        return gitInst
    }
}