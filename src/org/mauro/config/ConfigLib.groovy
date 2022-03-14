package org.mauro.config

import org.mauro.git.GitHub
import org.mauro.git.BitBucket

class ConfigLib implements Serializable {

    def GitHub githubInst = new GitHub()
    def BitBucket bitBucketInst = new BitBucket()
    def static ConfigLib configLibInst

    def public static ConfigLib getConfig () {
        if (configLibInst == null) {
            configLibInst = new ConfigLib()
        }
        return configLibInst
    }

    def public String executeTest () {
        return "this is a test"
    }
}