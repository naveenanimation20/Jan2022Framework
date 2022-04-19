pipeline {
    
    agent any
    
    stages{
        
        stage("Build"){
            steps{
                echo("Build project")
            }
        }
        
        stage("Run UTs"){
            steps{
                echo("run unit test cases")
            }
        }
        
        stage("Deploy to DEV"){
            steps{
                echo("dev deployment")
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("QA deployment")
            }
        }
        
        stage("Run Automation Regression Test"){
            steps{
                echo("running regression automation test cases")
            }
        }
        
         stage("Deploy to STAGE"){
            steps{
                echo("stage deployment")
            }
        }
        
        stage("Run Automation Sanity Test"){
            steps{
                echo("running automation sanity test cases")
            }
        }
        
         stage("Deploy to PROD"){
            steps{
                echo("PROD deployment")
            }
        }
        
    }
    
}