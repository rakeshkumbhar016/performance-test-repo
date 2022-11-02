import jenkins.model.*
jenkins = Jenkins.instance

#!/usr/bin/env groovy
node {

    stage('Initialise') {
        /* Checkout the scripts */
        checkout scm: [
                $class: 'GitSCM',
                userRemoteConfigs: [
                        [
                                url:"https://github.com/rakeshkumbhar016/performance-test-repo.git",
                                credentialsId: "CICDPerf-User"
                        ]
                ],
                branches: [[name: "main"]]
        ], poll: false
    }

    stage('Complete any setup steps') {
        echo "Complete set-up steps"
        echo "${octoperf_test_value}"
    }

    stage('Execute Performance Tests') {
        dir("${WORKSPACE}/scripts") {
            bat "c:/apache-jmeter/apache-jmeter/bin/jmeter.bat -n -t Shift-Left.jmx -l Shift-Left.jtl -Joptestvalue=${octoperf_test_value}"
        }
    }

    stage('Analyse Results') {
        echo "Analyse results"
    }
}
