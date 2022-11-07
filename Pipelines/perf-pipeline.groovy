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

    stage('Start any setup steps') {
        echo "Start set-up steps"
       // echo "${octoperf_test_value}"
    }

    stage('Execute Performance Tests') {
        dir("${WORKSPACE}/scripts") {
            bat "C:/JMeter_Tool/apache-jmeter-5.4.1/bin/jmeter.bat -n -t Site_Manager_All_API's.jmx -l SiteManager2.csv -e -o SiteManagerReport"
          //  -Joptestvalue=${octoperf_test_value
        }
    }
}
    stage('Analyse Results') {
        echo "Analyse results"
    }
    
    stage('Generate Performance Tred') {
        post{
            always{
                dir("/target/jmeter/results/"){
                    sh 'pwd'
                      perfReport '20210723-SiteManager2.csv'
                }
            
            }
        }
        }
       }
