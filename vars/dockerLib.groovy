import org.mauro.config.Constants
import org.mauro.config.DockerConfig

def public configRepository () {
    DockerConfig.config(this, credentials('registry-server'), credentials('registry-repo-dev'), credentials('registry-repo-stage'), credentials('registry-repo-prod'))
}

def public getRepositoryDev () {
    return DockerConfig.getRepositoryDev()
}

def public buildDockerImage (image) {
    unstash Constants.getStashName()
    sh "docker build -t ${getRepositoryDev()}${image} ." 
}

def public pushDockerImageDev (image) {
    if ("${REGISTRY_CRED_USR}" != '') {
        sh "docker login ${DockerConfig.getRegistryServer} -u ${REGISTRY_CRED_USR} -p ${REGISTRY_CRED_PSW}"
    }
    sh "docker push ${getRepositoryDev()}${image}"
}

def public getRepositoryStage () {
    return DockerConfig.getRepositoryStage()
}

def public getRepositoryProd () {
    return DockerConfig.getRepositoryProd()
}