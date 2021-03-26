# tprj/IaC
Directory for holding Infrastructure-as-Code assets such as CloudFormation stacks, Kubernetes manifests, makefiles etc.
### Instructions to Execute Operations using CURL Command
### SERVICE  s1
#### 1. Create user:
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/cuser.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/cuser.out
```
Example.
```sh
curl --location --request POST 'http://52.139.21.27/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '{ "user_name": "Sherlock", "user_email": "sholmes@baker.org", "user_phone": "6076076071" }' 
```
output:
`{"user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690"}`

#### 2. Login:
```sh
echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '$(BODY_UID)' > $(LOG_DIR)/apilogin.out
$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '$(BODY_UID)' > $(LOG_DIR)/apilogin.out | tee -a $(LOG_DIR)/apilogin.out

```
Example.
```sh
curl --location --request PUT 'http://52.139.21.27/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '{ "user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690" }'
```
output: 
`eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw`
`

#### 3. Logoff:
```sh
echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '$(BODY_TOKEN)' > $(LOG_DIR)/apilogoff.out
$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '$(BODY_TOKEN)' | tee -a $(LOG_DIR)/apilogoff.out
```
Example.
```sh
curl --location --request PUT 'http://52.139.21.27/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '{ "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw" }'
```
output:
`{}`

#### 4. Update User:
```sh
echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/uuser.out
$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/uuser.out
```
Example.
```sh
curl --location --request PUT 'http://52.139.21.27/api/v1/populate/user/d555a8cc-8e2d-4579-a15e-0edc986b7690' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw' --header 'Content-Type: application/json' --data-raw '{ "user_name": "Sherlock Holmes", "user_email": "sholmes@baker.org", "user_phone": "6076076071" }'
```
output:
`{{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:38:22 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"9AC25E34CB713VRE3K5F2IG3OJVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"9AC25E34CB713VRE3K5F2IG3OJVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`


#### 5. Delete User:
```sh
echo curl --location --request DELETE 'http://$(IGW)/api/v1/populate/user/$(USER_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/duser.out
$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/populate/user/$(USER_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/duser.out

```
Example.
```sh
curl --location --request DELETE 'http://52.139.21.27/api/v1/populate/user/d555a8cc-8e2d-4579-a15e-0edc986b7690' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:56:01 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"02O98TEAHR26RQI4QCDLOOEFHNVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"02O98TEAHR26RQI4QCDLOOEFHNVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`


#### 6. Get user:
```sh
echo curl --location --request GET 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/ruser.out
$(CURL) --location --request GET 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/ruser.out
```
Example:
```sh
curl --location --request GET 'http://52.139.21.27/api/v1/populate/user/d555a8cc-8e2d-4579-a15e-0edc986b7690' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
output:
`{"Count":1,"Items":[{"user_email":"sholmes@baker.org","user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690","user_name":"Sherlock","user_phone":"6076076071"}],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"195","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:39:25 GMT","server":"Server","x-amz-crc32":"882803775","x-amzn-requestid":"I16DDR7KGB98496RILDB4SEKGJVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"I16DDR7KGB98496RILDB4SEKGJVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":1}`


#### 7. Create Restaurant
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' > $(LOG_DIR)/crest.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' | tee -a $(LOG_DIR)/crest.out
```
Example:
```sh
curl --location --request POST 'http://52.139.21.27/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '{ "restaurant_name": "Tandoori Flames", "food_name": "pizza", "food_price" : "40" }'
```
Output:
`{"restaurant_id":"1a5defcc-78d6-4b8d-aca3-02f263321ff1"}`

#### 8. Update Restaurant
```sh
echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' > $(LOG_DIR)/urest.out
$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' | tee -a $(LOG_DIR)/urest.out
```
Example:
```sh
curl --location --request PUT 'http://52.139.21.27/api/v1/populate/restaurant/1a5defcc-78d6-4b8d-aca3-02f263321ff1' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw' --header 'Content-Type: application/json' --data-raw '{ "restaurant_name": "Pizza Town", "food_name": "pizza", "food_price" : "40" }'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:42:48 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"6B08L8TUIVC3H5R7NG1C4O2IQ7VV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"6B08L8TUIVC3H5R7NG1C4O2IQ7VV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 9. Delete Restaurant
```sh
echo curl --location --request DELETE 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/drest.out
$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/drest.out
```
Example:
```sh
curl --location --request DELETE 'http://52.139.21.27/api/v1/populate/restaurant/1a5defcc-78d6-4b8d-aca3-02f263321ff1' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:54:42 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"QJNKSDKHMGFAE5QDSREBU0ANOFVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"QJNKSDKHMGFAE5QDSREBU0ANOFVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 10. Get Restaurant
```sh
echo curl --location --request GET 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rrest.out
$(CURL) --location --request GET 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rrest.out
```
Example:
```sh
curl --location --request GET 'http://52.139.21.27/api/v1/populate/restaurant/1a5defcc-78d6-4b8d-aca3-02f263321ff1' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"Count":1,"Items":[{"food_name":"pizza","food_price":"40","restaurant_id":"1a5defcc-78d6-4b8d-aca3-02f263321ff1","restaurant_name":"Tandoori Flames"}],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"193","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:42:03 GMT","server":"Server","x-amz-crc32":"3636568432","x-amzn-requestid":"2SDR6QR138F5NT2T816QTQ655BVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"2SDR6QR138F5NT2T816QTQ655BVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":1}`


### SERVICE s2
#### 1. Create Order:
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/orders' --header 'Content-Type: application/json' --data-raw '$(BODY_ORDER)' > $(LOG_DIR)/corder.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/orders' --header 'Content-Type: application/json' --data-raw '$(BODY_ORDER)' | tee -a $(LOG_DIR)/corder.out
```
Example:
```sh
curl --location --request POST 'http://52.139.21.27/api/v1/orders/' --header 'Content-Type: application/json' --data-raw '{ "user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690", "restaurant_id":"1a5defcc-78d6-4b8d-aca3-02f263321ff1", "food_name": "pizza" }' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"order_id":"a5781b90-6678-40fb-8965-533b665a9f82"}`

#### 2. Delete Order:
```sh
echo curl --location --request DELETE 'http://$(IGW)/api/v1/orders/$(ORDER_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/dorder.out
$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/orders/$(ORDER_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/dorder.out
```
Example:
```sh
curl --location --request DELETE 'http://52.139.21.27/api/v1/orders/a5781b90-6678-40fb-8965-533b665a9f82' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw' 
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:53:59 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"B495RA3OBG9DCRKBONGRFIJK0FVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"B495RA3OBG9DCRKBONGRFIJK0FVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 3. Get Order
```
echo curl --location --request GET 'http://$(IGW)/api/v1/orders/$(ORDER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rorder.out
$(CURL) --location --request GET 'http://$(IGW)/api/v1/orders/$(ORDER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rorder.out
```
Example:
```sh
curl --location --request GET 'http://52.139.21.27/api/v1/orders/a5781b90-6678-40fb-8965-533b665a9f82' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
output: 
`{"Count":1,"Items":[{"food_name":"pizza","order_id":"a5781b90-6678-40fb-8965-533b665a9f82","restaurant_id":"1a5defcc-78d6-4b8d-aca3-02f263321ff1","user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690"}],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"238","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:46:05 GMT","server":"Server","x-amz-crc32":"821038353","x-amzn-requestid":"TR9LKT2FN660QL35B0ENDAUST3VV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"TR9LKT2FN660QL35B0ENDAUST3VV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":1}`

#### SERVICE s3

#### 1. Create Bills:
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '$(BODY_BILLS)' > $(LOG_DIR)/cbils.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '$(BODY_BILLS)' | tee -a $(LOG_DIR)/cbills.out

```
Example:
```sh
curl --location --request POST 'http://52.139.21.27/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '{ "payment_method": "Card", "discount_applied":"10", "payment_amount":"100", "food_name": "pizza", "user_id":"d555a8cc-8e2d-4579-a15e-0edc986b7690", "order_id":"a5781b90-6678-40fb-8965-533b665a9f82", "restaurant_id":"1a5defcc-78d6-4b8d-aca3-02f263321ff1" }' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"payment_id":"e46b40fb-b8a7-4d11-b727-f8425c432e25"}`

#### 2. Delete Bills:
```sh
echo curl --location --request DELETE 'http://$(IGW)/api/v1/orders/$(PAYEMENT_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/dbills.out
$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/orders/$(PAYEMENT_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/dbills.out

```
Example:
```sh
curl --location --request DELETE 'http://52.139.21.27/api/v1/bills/e46b40fb-b8a7-4d11-b727-f8425c432e25' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:53:17 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"TSRS6SN183P4HK4L5OM2JTHSTJVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"TSRS6SN183P4HK4L5OM2JTHSTJVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 3. Get Bills
```
echo curl --location --request GET 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rbills.out
$(CURL) --location --request GET 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rbills.out
```
Example:
```sh
curl --location --request GET 'http://52.139.21.27/api/v1/bills/e46b40fb-b8a7-4d11-b727-f8425c432e25' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"Count":0,"Items":[],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"39","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:48:35 GMT","server":"Server","x-amz-crc32":"3413411624","x-amzn-requestid":"8TQA1RUPU749QCUJMEMTIPBTSNVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"8TQA1RUPU749QCUJMEMTIPBTSNVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":0}`

### SERVICE s4
#### 1. Get Discount
```sh
echo curl --location request GET 'http://$(IGW)/api/v1/discount/show_discount?payment_id=$(PAYMENT_ID)&order_id=$(ORDER_ID)&user_id=$(USER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rdiscount.out
$(CURL) --location request GET 'http://$(IGW)/api/v1/discount/show_discount?payment_id=$(PAYMENT_ID)&order_id=$(ORDER_ID)&user_id=$(USER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rdiscount.out
```
Example:
```sh
curl --location request GET 'http://52.139.21.27/api/v1/discount/show_discount?payment_id=e46b40fb-b8a7-4d11-b727-f8425c432e25&order_id=a5781b90-6678-40fb-8965-533b665a9f82&user_id=d555a8cc-8e2d-4579-a15e-0edc986b7690'
```
output:`10`
`   `
### SERVICE s5
#### 1. Create Delivery:
```sh
echo curl --location --request POST 'http://$(IGW)/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '$(BODY_DELIVERY)' > $(LOG_DIR)/cdelivery.out
$(CURL) --location --request POST 'http://$(IGW)/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '$(BODY_DELIVERY)' | tee -a $(LOG_DIR)/cdelivery.out
```
Example:
```sh
curl --location --request POST 'http://52.139.21.27/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '{ "order_id":"a5781b90-6678-40fb-8965-533b665a9f82", "driver_name":"John", "predicted_delivery_time":"25" }'
```
output:
`{"delivery_id":"0fed398b-b5be-4e16-a4c9-bebf9dc42422"}`


#### 2. Delete Delivery:
```sh
echo curl --location --request DELETE 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/ddelivery.out
$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/ddelivery.out
```
Example:
```sh
curl --location --request DELETE 'http://52.139.21.27/api/v1/delivery/0fed398b-b5be-4e16-a4c9-bebf9dc42422' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"2","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:51:58 GMT","server":"Server","x-amz-crc32":"2745614147","x-amzn-requestid":"U9KSQ8IQ6DJA4HA9JODPDCJOHVVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"U9KSQ8IQ6DJA4HA9JODPDCJOHVVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0}}`

#### 3. Get Delivery:
```sh
echo curl --location --request GET 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rdelivery.out
$(CURL) --location --request GET 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rdelivery.out
```
Example:
```sh
curl --location --request GET 'http://52.139.21.27/api/v1/delivery/0fed398b-b5be-4e16-a4c9-bebf9dc42422' --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDU1NWE4Y2MtOGUyZC00NTc5LWExNWUtMGVkYzk4NmI3NjkwIiwidGltZSI6MTYxNjc0NzgwMC42NDMxMzY3fQ.Dnv0DaDYT-icDQhk8yImUw5uSYuBipjkI0rja5YB7Lw'
```
Output:
`{"Count":0,"Items":[],"ResponseMetadata":{"HTTPHeaders":{"connection":"keep-alive","content-length":"39","content-type":"application/x-amz-json-1.0","date":"Fri, 26 Mar 2021 08:51:02 GMT","server":"Server","x-amz-crc32":"3413411624","x-amzn-requestid":"0RN8L8OB59B0HOOSC8ECFSJG8BVV4KQNSO5AEMVJF66Q9ASUAAJG"},"HTTPStatusCode":200,"RequestId":"0RN8L8OB59B0HOOSC8ECFSJG8BVV4KQNSO5AEMVJF66Q9ASUAAJG","RetryAttempts":0},"ScannedCount":0}`





