// import org.mauro.config.Config
import org.mauro.config.Constants
// import org.mauro.git.GitRetriever
import org.mauro.templating.BuilderRetriever
// import org.mauro.Tools

def public runMutationTests () {
    unstash Constants.getStashName()
    BuilderRetriever.getBuilder().runMutationTests()
}

def public runDependencyCheck () {
    unstash Constants.getStashName()
    BuilderRetriever.getBuilder().runDependencyCheck()
}

def public runIntegrationTests ()  {
    sh "echo 'add test execution here'"
}