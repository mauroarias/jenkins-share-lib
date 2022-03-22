package org.mauro.templating

import org.mauro.config.Config

class BuilderRetriever implements Serializable {

    def static builderInst

    def public static configBuider (steps) {
        if (Config.getTemplateName().equals('maven')) {
            builderInst = new Maven()
        } else {
            error('Builder engine not supported...!')
        }
        builderInst.setSteps(steps)
        return builderInst
    }

    def public static getBuilderInst () {
        if (builderInst == null) {
            error('remote not configured...!')
        }
        return builderInst
    }
}