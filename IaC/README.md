# tprj/IaC
Directory for holding Infrastructure-as-Code assets such as CloudFormation stacks, Kubernetes manifests, makefiles etc.


# tprj/IaC
Directory for holding Infrastructure-as-Code assets such as CloudFormation stacks, Kubernetes manifests, makefiles etc.


### Instructions to Execute Operations using CURL Command
### SERVICE  s1
#### 1. Create user:
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/user/' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/cuser.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/user/' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/cuser.out
```
Example.
```sh
$ curl --location --request POST 'http://52.139.15.115/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '{"user_name": "abhishek", "user_email": "abc@sfu.ca", "user_phone": "6047218764"}'
```
output:
`Output:{"user_id":"ee02ff57-4847-4927-a8c8-968f5075b75e"}`

#### 2. Login:
```sh

```
Example.
```sh
$ curl --location --request PUT 'http://52.139.15.115/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '{"user_id":"ecd5ff4d-7635-459b-b56c-96b50b17b7d3"}'
```
output: 
`OeyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZWNkNWZmNGQtNzYzNS00NTliLWI1NmMtOTZiNTBiMTdiN2QzIiwidGltZSI6MTYxNjM2MzUyNC4xMzg0OTE2fQ.CJZgRuqj9A_TgOo4OTv0cQt9P1mnEMKMjRxOGITi7js`

#### 3. Logoff:
```sh

```
Example.
```sh
curl --location --request PUT 'http://52.139.15.115/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '{"jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZWNkNWZmNGQtNzYzNS00NTliLWI1NmMtOTZiNTBiMTdiN2QzIiwidGltZSI6MTYxNjM2MzUyNC4xMzg0OTE2fQ.CJZgRuqj9A_TgOo4OTv0cQt9P1mnEMKMjRxOGITi7js"}'
```
output:
`{}`

#### 4. Update User:
```sh

```
Example.
```sh
curl --location --request PUT 'http://52.139.15.115/api/v1/populate/user/ee02ff57-4847-4927-a8c8-968f5075b75e' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNmI3NGU1MTYtMjEyOS00YWI4LWJiZWMtYTg0YzI4MDg4Nzc0IiwidGltZSI6MTYxNjMxNTQ4OC43NjcxNDkyfQ.xzCoEcgkuLj483PTqPf1aVDFTD150VlvYBzDycMQuEw' --header 'Content-Type: application/json' --data-raw '{"user_name": "abhi1223", "user_email": "abc1111@sfu.ca", "user_phone": "112233555"}'
```
output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:04:13 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"OL8S5OPE05INVEQL3NJ32BK6EVVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"OL8S5OPE05INVEQL3NJ32BK6EVVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 5. Delete User:
```sh

```
Example.
```sh
$ curl --location --request DELETE 'http://52.139.15.115/api/v1/populate/user/ecd5ff4d-7635-459b-b56c-96b50b17b7d3' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNmI3NGU1MTYtMjEyOS00YWI4LWJiZWMtYTg0YzI4MDg4Nzc0IiwidGltZSI6MTYxNjMxNTQ4OC43NjcxNDkyfQ.xzCoEcgkuLj483PTqPf1aVDFTD150VlvYBzDycMQuEw'
```
output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:08:01 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"260O4E8OUCLFPCQIVPTRPJTR73VV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"260O4E8OUCLFPCQIVPTRPJTR73VV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`


#### 6. Get user:
```sh

```
Example:
```sh
curl --location --request GET 'http://52.139.15.115/api/v1/populate/user/ee02ff57-4847-4927-a8c8-968f5075b75e' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1'
```
output:
`{"Count":1,"Items":[{"user_email":"abc1111@sfu.ca","user_id":"ee02ff57-4847-4927-a8c8-968f5075b75e","user_name":"abhi1223","user_phone":"112233555"}],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"191","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:31:06 GMT","server":"Server","x-amz-crc32":"4047070539","x-amzn-requestid":"HD89KMH6UMMSM2AFVM9D9G4SHVVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"HD89KMH6UMMSM2AFVM9D9G4SHVVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":1}`

#### 7. Create Restaurant
```sh
```
Example:
```sh
curl --location --request POST 'http://52.139.15.115/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '{"restaurant_name": "Tandoori Flames", "food_name": "Pav Bhaji", "food_price" : "40"}'
```
Output:
`{"restaurant_id":"d7f4f676-2155-47a7-9200-c12cfbf8c67a"}`

#### 8. Update Restaurant
```sh
```
Example:
```sh
curl --location --request PUT 'http://52.139.15.115/api/v1/populate/restaurant/dcea700a-b5f1-4321-b148-2d563e63ee82' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZWNkNWZmNGQtNzYzN'  --header 'Content-Type: application/json' --data-raw '{"restaurant_name": "Tandoori Flames", "food_name": "Pav Bhaji", "food_price":"35"}'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:12:47 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"5OH93LLUP8TQGA6B0V43KOHBFNVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"5OH93LLUP8TQGA6B0V43KOHBFNVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 9. Delete Restaurant
```sh
```
Example:
```sh
curl --location --request DELETE 'http://52.139.15.115/api/v1/populate/restaurant/dcea700a-b5f1-4321-b148-2d563e63ee82' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:14:06 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"H5BBAH7VS1R69UIQHT0G7JPAVBVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"H5BBAH7VS1R69UIQHT0G7JPAVBVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 10. Get Restaurant
```sh
```
Example:
```
$ curl --location --request GET 'http://52.139.15.115/api/v1/populate/restaurant/a38962b2-59bb-4723-a15e-d99fb7bfcaad' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1'
```
Output:
`{"Count":1,"Items":[{"food_name":"Pav Bhaji","food_price":"40","restaurant_id":"a38962b2-59bb-4723-a15e-d99fb7bfcaad","restaurant_name":"Tandoori Flames"}],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"197","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:27:23 GMT","server":"Server","x-amz-crc32":"3248517746","x-amzn-requestid":"FK6UQ6F842CQJB0119AE5DBSKFVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"FK6UQ6F842CQJB0119AE5DBSKFVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":1}`

### SERVICE s2
#### 1. Create Order:
```sh
```
Example:
```
curl --location --request POST 'http://52.139.15.115/api/v1/orders/' --header 'Content-Type: application/json' --data-raw '{"user_id":"123", "restaurant_id":"456", "food_name":"pizza"}' --header 'Authorization: Bearer 123mytoken789'
```
Output:
`{"order_id":"49981eab-98e7-478a-9ae9-cf12685bacca"}`

#### 2. Delete Order:
```sh
```
Example:
```
$ curl --location --request DELETE 'http://52.139.15.115/api/v1/orders/49981eab-98e7-478a-9ae9-cf12685bacca' --header 'Authorization: Bearer 123mytoken789'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 22:41:26 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"S476BLH4GOS5T53UV5FR7N1DR7VV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"S476BLH4GOS5T53UV5FR7N1DR7VV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### SERVICE s3

#### 1. Create Bills:
```sh
```
Example:
```
curl --location --request POST 'http://52.139.15.115/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '{"payment_method": "Card", "discount_applied":"10", "payment_amount":"100000", "food_name":"pizza", "user_id":"ee02ff57-4847-4927-a8c8-968f5075b75e","order_id":"DEF456","restaurant_id":"XYZ789"}' --header 'Authorization: Bearer 123mytoken'
```
Output:
`{"payment_id":"735dc5f3-62dd-478d-b6c8-60d7a8890f85"}`

#### 2. Delete Bills:
```sh
```
Example:
```
curl --location --request DELETE "http://52.139.15.115/api/v1/bills/4775b1f9-88bd-4143-96df-9a085c84d1bd" --header "Authorization: Bearer 123mytoken"
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Sun, 21 Mar 2021 23:59:14 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"56PBVA6ROEKFOH2PUR42I3LGS3VV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"56PBVA6ROEKFOH2PUR42I3LGS3VV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

### SERVICE s4
#### 1. Get Discount
```sh
```
Example:
```

```
output:
`   `
### SERVICE s5
#### 1. Create Delivery:
```sh
```
Example:
```
curl --location --request POST 'http://52.139.15.115/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '{"order_id":"972","driver_name":"John","predicted_delivery_time":"25"}' --header 'Authorization: Bearer 123mytoken789'
```
output:
`{"delivery_id":"1dff32a9-fdd6-4cc8-9994-203764804c63"}`


#### 2. Delete Delivery:
```sh
```
Example:
```
curl --location --request DELETE 'http://52.139.15.115/api/v1/delivery/0b43d2d3-6ae7-4d20-b99c-c66e2ea01fa3' --header 'Authorization: Bearer 123mytoken789'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Mon, 22 Mar 2021 01:06:07 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"KM7UE7PTVEPFVHCEVA114S2JJJVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"KM7UE7PTVEPFVHCEVA114S2JJJVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`





