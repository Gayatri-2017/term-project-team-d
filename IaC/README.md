# tprj/IaC
Directory for holding Infrastructure-as-Code assets such as CloudFormation stacks, Kubernetes manifests, makefiles etc.


### INstruction to execute operations using CURL command

### S1

#### 1.Create user:

'''
echo curl --location --request POST 'http://$(IGW)/api/v1/user/' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/cuser.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/user/' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/cuser.out
...

Example.'''
$ curl --location --request POST 'http://52.139.15.115/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '{"user_name": "abhishek", "user_email": "abc@sfu.ca", "user_phone": "6047218764"}'
'''

output: 'Output:{"user_id":"ee02ff57-4847-4927-a8c8-968f5075b75e"}'




