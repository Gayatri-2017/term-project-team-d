
# CMPT 756 Project: 

## Problem Domain and Application

### Great Feasts
Given the fast paced environment that we are living in and the advent of the technologies that have made our lives dependent on it, we propose the solution for a food delivery service “Great Feasts”.  Especially during the Pandemic era, ordering food online at home has increased, and almost become a common thing thus making it one of the most essential services required to be available all the time. Our application provides reliable, scalable and available Food Delivery service to its customers using the concepts learnt in Cloud & Distributed System course. 


###  Entities
   The entities in our application are
   
##### 1. User:
   A user is a customer who orders food or updates other tables.    
##### 2. Restaurants: 
   A food outlet consisting of various food items and their prices.   
##### 3. Order:
   Stores the order of one food item in a restaurant made by a customer
##### 4. Payment:
   Stores payment-related information made by a customer for an order. 
##### 5. Deliveries:
   Stores Delivery related information for an order



### Operations

We have the following 5 operations defined: 

##### 1. POPULATE
The admin User can populate restaurant and user details

##### 2. CART
The User can order food from a Restaurant

##### 3. BILLS
The User can pay the Restaurant

##### 4. DISCOUNT
Check if a discount/offer is available for a specific order

##### 5. DELIVERY
Each Order is mapped to a Delivery

------------

Interaction of entities with respect to operations can be shown as:

![Alt text](readme_images/operations.jpeg?raw=true )

------------

Project Organization
------------

    └── term-project-team-d
    ├── code
    │   ├── db
    │   ├── gatling
    │   ├── helper
    │   ├── README.md
    │   ├── s1
    │   ├── s2
    │   ├── s3
    │   ├── s4
    │   └── s5
    ├── docs
    │   └── README.md
    ├── IaC
    │   ├── cluster
    │   ├── Makefiles
    │   └── README.md
    ├── readme_images
    │   └── operations.jpeg
    ├── README.md
    └── scripts
        ├── call-sed.sh
        ├── gatling
        ├── gatling.sh
        ├── getip.sh
        ├── kill-gatling.sh
        ├── list-gatling.sh
        └── process-templates.sh
      
------------

## Tools used
The project intends to use the following:
```
Flask
Docker
Azure Kubernetes 
AWS DynamoDB
Grafana
Prometheus
Gatling
Istio
REST API

```
------------

Instructions for Project Execution
------------

####  Step 1: Clone the repository using the following command.
```
git clone https://github.com/scp-2021-jan-cmpt-756/term-project-team-d.git
```

####  Step 2:  Copy the file `cluster/tpl-vars-blank.txt` to cluster/tpl-vars.txt and fill in all the required values in tpl-vars.txt
- AWS keys
- Github signon
- JAVA_HOME
- GAT_HOME

####  Step 3:  Navigate to IaC/Makfiles folder and instantiate templates
```
make -f k8s-tpl.mak templates
```
####  Step 4:  Update AWS credential as follows
- Add AWS crendetials to `~/.aws/credentials file`
- Add AWS credentials to `tpl-vars.mak` in IaC/Makefiles folder

####  Step 5:  Setup DynamoDB tables- Navigate to IaC/cluster folder and run the following command
```
aws cloudformation create-stack --stack-name proj-scp-2021-jan-cmpt-756 --template-body file://../cluster/cloudformationdynamodb.json
```
#### Step 6: Start a new cluster on Azure- Navigate to IaC/Makefiles folder and run the following command
```
make -f az.mak start
```

#### Step 7: login to Azure
```
az login
```
#### Step 8: Provision the cluster
```
make -f k8s.mak provision
```
- Ensure all the pods are running

#### Step 9: Get the external IP by running following command
```
kubectl -n istio-system get service istio-ingressgateway
```
- copy the external IP from the output and store in $IGW variable in api.mak file 
#### Step 10: To perform various operations related to interactions between DynomoDB tables please refer [Readme.md]( https://github.com/scp-2021-jan-cmpt-756/term-project-team-d/blob/main/IaC/README.md) in IaC folder. 

#### Step 11: Gatling simulations call each of the entities through a REST API call thus, hitting the API hosted on the Azure cloud. Run Gatling simulations using following commands from the root directory.
```
scripts/gatling.sh <number of records> CoverageUserSim
scripts/gatling.sh <number of records> CoverageRestaurantSim
scripts/gatling.sh <numbe rof records> CoverageOrdersSim
scripts/gatling.sh <number of records> CoverageDiscountSim 
scripts/gatling.sh <number of records> CoverageBillsSim 
scripts/gatling.sh <number of records> CoverageDeliverySim
```
- for coverage simulation we can use  1-5 number of records

#### Step 12: Load simulation is run using Gatling Standalone v3.4.2 and Scala 2.12.2. Run the following commands for a Linux terminal with root access. (You can also follow the online instructions to install Gatling standalone for other OS)
```
wget http://scala-lang.org/files/archive/scala-2.12.2.deb
dpkg -i scala-2.12.2.deb
wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/3.4.2/gatling-charts-highcharts-bundle-3.4.2-bundle.zip
cd code
unzip gatling-charts-highcharts-bundle-3.4.2-bundle.zip
```
- Make a copy of the existing folder gatling-charts-highcharts-bundle-3.4.2 in the code directory of the repository (which you will have if you cloned the repository before unzipping)
- Modify the Gatling home path to be the unzipped folder path in tpl-vars.txt and run k8s-tpl.mak as shown in step 3 (Alternatively, run the following command: export GATLING_HOME=<path to the unzipped Gatling folder>)
- Replace gatling.sh in you local installation with the one from the repository at code/gatling-charts-highcharts-bundle-3.4.2/bin/gatling.sh
- Replace the folder user-files in your local installation with the one from the repository at code/gatling-charts-highcharts-bundle-3.4.2/user-files/  (Replace the scala file and the csv files in your local installation with the ones in the repository)
- Copy the external IP from Step 9 and modify the baseUrl parameter in the httpProtocol variable for the scala file code/gatling-charts-highcharts-bundle-3.4.2/user-files/simulations/proj756/ReadTables.scala
- Once all the above changes are done, run gatling.sh using the following commands (assuming you are in the folder term-project-team-d)
```
cd code/gatling-charts-highcharts-bundle-3.4.2/bin
./gatling.sh   
```
- Select option 0 for the LoadUserSim scenario and give the test run an optional name
- Verify that the GATLING_HOME is set correctly and pointing to your unzipped folder in the code directory
- The Gatling results will be stored in the results folder

#### Step 13: Failure analysis
- Move to yamls/ directory. To inject failure, keep the ./gatling.sh running from previous step. Choose the type of failure from s1-failure-<failure_type>.yaml and run the following
```
kubectl apply -f s1-failure-timeout.yaml -n c756ns
```
- To get the original version, reapply s1-vs.yaml
#### Step 14: Clean up steps- delete AWS stackID, stop Azure cluster and the hosted services
```
aws cloudformation delete-stack --stack-name proj-scp-2021-jan-cmpt-756
make -f az.mak stop
make -f k8s.mak scratch
```
