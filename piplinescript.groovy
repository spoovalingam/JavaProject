pipeline {
    agent any 
    stages {
		stage('Check Out') { 
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Git_ID', url: 'https://github.com/spoovalingam/StaticWeb']]])
            }
        }
        stage('Build') { 
            steps {
                sh "cd ${JENKINS_HOME}/workspace/demo-web"
                sh 'jar -cvf StaticWeb.war *'
				sh 'docker build --rm -f Dockerfile -t poovalingam/staticweb:1.0.1 .'
            }
        }
        stage('Push Docker') { 
            steps {
                 withCredentials([usernamePassword(credentialsId: 'Dockerhub_ID', passwordVariable: 'password', usernameVariable: 'username')]) {
                sh "docker login docker.io -u ${username} -p ${password}"
                sh "whoami"
                sh 'docker push poovalingam/staticweb:1.0.1'
                } 
            }
        }
        stage('Testing') { 
            steps {
                sh "cd ${JENKINS_HOME}/workspace/demo-web"
			    //sh 'java -jar SamplePOC.jar'
                //echo result  
                //kubernetesDeploy configs: 'kubeconfig.json', dockerCredentials: [[credentialsId: 'Dockerhub_ID']], kubeConfig: [path: '.'], kubeconfigId: 'Kube_ID', secretName: '', secretNamespace: 'nginx-ingress', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                //def kubeconfig = ['kubeconfig','/var/lib/jenkins/workspace/demo-web/kubeconfig.json']
				sh "kubectl -n nginx-ingress replace -f staticweb-svc-deploy.ymal --kubeconfig='${JENKINS_HOME}/workspace/demo-web/kubeconfig.json'"
            }
        }
    }
}