# Jenkins share library
 This share library gives some common capabilities to [template-jenkins-lib](https://github.com/mauroarias/template-jenkins-lib) and [pipeline-jenkins-lib](https://github.com/mauroarias/pipeline-jenkins-lib) libraries how all work together to gives functionality to [devops-develpment-platform](https://github.com/mauroarias/devops-development-platform).

This library, should loaded dinamically as:

    library identifier: "jenkins-share-lib@${libVersion}", retriever: 
	    modernSCM(
		    [$class: 'GitSCMSource',
		    remote: 'https://github.com/mauroarias/jenkins-share-lib.git'])
where **${libVersion}** is the the version of the lib's branch. 

> for latest use master **branch**
 
## supported capabilities

 - **Docker basic capabilities**
 - **Git Api capabilities:** gitHub & bitBucket support to: create repository, commit, push, clone repository, check existing repositories, get repository list, init a repo, etc.
 - **Jenkins Api capabilities:** giveing support to: download CLI, get list of projects, create jobs, check existing jobs & projects, stash, archive an actifact and publish html reports.
 - **templating capabilities:** abstraction to allow cross code compilation thought severals programing languages.

  
