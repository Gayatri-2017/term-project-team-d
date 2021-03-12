
# CMPT 756 Project: 

## Problem Domain and Application

### Great Feasts
Given the fast paced environment that we are living in and the advent of the technologies that have made our lives dependent on it, we propose the solution for a food delivery service “Great Feasts”.  Especially during the Pandemic era, ordering food online at home has increased, and almost become a common thing thus making it one of the most essential services required to be available all the time. Our application provides reliable, scalable and available Food Delivery service to its customers using the concepts learnt in Cloud & Distributed System course. 


###  Entities
   The entities in our application are
   
##### 1. User:
   A use is a customer who orders food or updates other tables.    
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

![Alt text](readme_images/operations.jpeg?raw=true )


Project Organization
------------

    └── term-project-team-d 
    ├── code
    │   ├── db
    │   ├── gatling
    │   ├── helper
    │   ├── logs
    │   ├── s1
    │   ├── s2
    │   ├── s3
    │   ├── s4
    │   └── s5
    ├── docs
    │   └── README.md
    ├── IaC
    │   ├── cluster
    │   └── Makefiles
    ├── readme_images
    │   └── operations.jpeg
    ├── README.md
    └── scripts
        └── script1.sh 

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

