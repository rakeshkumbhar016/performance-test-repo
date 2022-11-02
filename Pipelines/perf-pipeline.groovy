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
       // echo "${octoperf_test_value}"
    }

    stage('Execute Performance Tests') {
        dir("${WORKSPACE}/scripts") {
            bat "C:/JMeter_Tool/apache-jmeter-5.4.1/bin/jmeter.bat -n -t SiteManager_All_api's_Randon_Final.jmx -l Shift-Left.jtl}"
          //  -Joptestvalue=${octoperf_test_value
        }
    }

    stage('Analyse Results') {
        echo "Analyse results"
    }
}
