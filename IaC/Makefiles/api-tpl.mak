#
# Front-end to bring some sanity to the litany of tools and switches
# in calling the sample application from the command line.
#
# This file covers off driving the API independent of where the cluster is
# running.
# Be sure to set your context appropriately for the log monitor.
#
# The intended approach to working with this makefile is to update select
# elements (body, id, IP, port, etc) as you progress through your workflow.
# Where possible, stodout outputs are tee into .out files for later review.
#


KC=kubectl
CURL=curl
NS=c756ns

# Keep all the logs out of main directory
LOG_DIR=../logs

# look these up with 'make ls'
# You need to specify the container because istio injects side-car container
# into each pod.
# s1: service1; s2: service2; s3: service3; s4: service4; s5: service5; db: cmpt756db

S1POD=pod/team-d-cmpt756s1-8557865b4b-jnwrj
S1PODCONT=team-d-cmpt756s1

S2POD=pod/team-d-cmpt756s2-8557865b4b-jnwrj
S2PODCONT=team-d-cmpt756s2

S3POD=pod/team-d-cmpt756s3-8557865b4b-jnwrj
S3PODCONT=team-d-cmpt756s3

S4POD=pod/team-d-cmpt756s4-8557865b4b-jnwrj
S4PODCONT=team-d-cmpt756s4

S5POD=pod/team-d-cmpt756s5-8557865b4b-jnwrj
S5PODCONT=team-d-cmpt756s5

# show deploy and pods in current ns; svc of cmpt756 ns
ls: showcontext
	$(KC) -n $(NS) get gw,deployments,pods
	$(KC) -n $(NS) get svc

s1logs:
	$(KC) -n $(NS) logs $(S1POD) -c $(S1PODCONT)

s2logs:
	$(KC) -n $(NS) logs $(S2POD) -c $(S2PODCONT)

s3logs:
	$(KC) -n $(NS) logs $(S3POD) -c $(S3PODCONT)

s4logs:
	$(KC) -n $(NS) logs $(S4POD) -c $(S4PODCONT)

s5logs:
	$(KC) -n $(NS) logs $(S5POD) -c $(S5PODCONT)

#
# Replace this with the external IP/DNS name of your cluster
#
# In all cases, look up the external IP of the istio-ingressgateway LoadBalancer service
# You can use either 'make -f eks.m extern' or 'make -f mk.m extern' or
# directly 'kubectl -n istio-system get service istio-ingressgateway'
#
#IGW=172.16.199.128:31413
#IGW=10.96.57.211:80
#IGW=a344add95f74b453684bcd29d1461240-517644147.us-east-1.elb.amazonaws.com:80
IGW=EXTERN

# keep these ones around
USER_ID=0d2a2931-8be6-48fc-aa9e-5a0f9f536bd3

# it's convenient to have a second set of id to test deletion (DELETE uses these id with the suffix of 2)
USER_ID2=9175a76f-7c4d-4a3e-be57-65856c6bb77e

# keep these ones around
RESTAURANT_ID=0d2a2931-8be6-48fc-aa9e-5a0f9f536bd3

# it's convenient to have a second set of id to test deletion (DELETE uses these id with the suffix of 2)
RESTAURANT_ID2=9175a76f-7c4d-4a3e-be57-65856c6bb77e

# keep these ones around
ORDER_ID=0d2a2931-8be6-48fc-aa9e-5a0f9f536bd3

# it's convenient to have a second set of id to test deletion (DELETE uses these id with the suffix of 2)
ORDER_ID2=9175a76f-7c4d-4a3e-be57-65856c6bb77e

# keep these ones around
PAYMENT_ID=0d2a2931-8be6-48fc-aa9e-5a0f9f536bd3

# it's convenient to have a second set of id to test deletion (DELETE uses these id with the suffix of 2)
PAYMENT_ID2=9175a76f-7c4d-4a3e-be57-65856c6bb77e

# keep these ones around
DELIVERY_ID=0d2a2931-8be6-48fc-aa9e-5a0f9f536bd3

# it's convenient to have a second set of id to test deletion (DELETE uses these id with the suffix of 2)
DELIVERY_ID2=9175a76f-7c4d-4a3e-be57-65856c6bb77e


FOOD_NAME=pizza

# stock body & fragment for API requests
BODY_USER= { \
"user_name": "Sherlock", \
"user_email": "sholmes@baker.org", \
"user_phone": "6076076071"\
}

BODY_RESTAURANT= { \
"restaurant_name": "Tandoori Flames", \
"food_name": "$(FOOD_NAME)",\
"food_price" : "40" \
}


BODY_ORDER= {\
"user_id":"$(USER_ID)", \
"restaurant_id":"$(RESTAURANT_ID)", \
"food_name": "$(FOOD_NAME)"\
}


BODY_PAYMENT= {\
"user_id":"$(USER_ID)", \
"restaurant_id":"$(RESTAURANT_ID)", \
"food_name": "$(FOOD_NAME)"\
}

BODY_UID= { \
"user_id":"$(USER_ID)" \
}

BODY_RID= { \
"restaurant_id":"$(RESTAURANT_ID)" \
}

BODY_OID= { \
"order_id":"$(ORDER_ID)" \
}

BODY_BILLS={\
	"payment_method": "Card",\
	"discount_applied":"10",\
	"payment_amount":"100",\
	"food_name": "$(FOOD_NAME)", \
	"user_id":"$(USER_ID)",\
	"order_id":"$(ORDER_ID)",\
	"restaurant_id":"$(RESTAURANT_ID)"\
	}

BODY_DELIVERY={ \
"order_id":"$(ORDER_ID)", \
"driver_name":"John",\
"predicted_delivery_time":"25"\
}


BODY_PID= { \
"payment_id":"$(PAYMENT_ID)" \
}

# this is a token for user
TOKEN=Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMDI3Yzk5ZWYtM2UxMi00ZmM5LWFhYzgtMTcyZjg3N2MyZDI0IiwidGltZSI6MTYwMTA3NDY0NC44MTIxNjg2fQ.hR5Gbw5t2VMpLcj8yDz1B6tcWsWCFNiHB_KHpvQVNls

BODY_TOKEN={ \
    "jwt": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMDI3Yzk5ZWYtM2UxMi00ZmM5LWFhYzgtMTcyZjg3N2MyZDI0IiwidGltZSI6MTYwMTA3NDY0NC44MTIxNjg2fQ.hR5Gbw5t2VMpLcj8yDz1B6tcWsWCFNiHB_KHpvQVNls" \
}

#**************************************************** Service  s1 User****************************************************

# POST is used for user (apipost) to create a new record
cuser:
	echo curl --location --request POST 'http://$(IGW)/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/cuser.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/populate/user' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/cuser.out

# PUT is used for user (update) to update a record
uuser:
	echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' > $(LOG_DIR)/uuser.out
	$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_USER)' | tee -a $(LOG_DIR)/uuser.out

# DELETE is used with user to delete a record
duser:
	echo curl --location --request DELETE 'http://$(IGW)/api/v1/populate/user/$(USER_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/duser.out
	$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/populate/user/$(USER_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/duser.out

# GET is used with user to read a record
ruser:
	echo curl --location --request GET 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/ruser.out
	$(CURL) --location --request GET 'http://$(IGW)/api/v1/populate/user/$(USER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/ruser.out

# PUT is used for login/logoff too
apilogin:
	echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '$(BODY_UID)' > $(LOG_DIR)/apilogin.out
	$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/login' --header 'Content-Type: application/json' --data-raw '$(BODY_UID)' | tee -a $(LOG_DIR)/apilogin.out

apilogoff:
	echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '$(BODY_TOKEN)' > $(LOG_DIR)/apilogoff.out
	$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/logoff' --header 'Content-Type: application/json' --data-raw '$(BODY_TOKEN)' | tee -a $(LOG_DIR)/apilogoff.out

#**************************************************** Service  s1 Restaurant****************************************************

# POST is used for restaurant (apipost) to create a new record
crest:
	echo curl --location --request POST 'http://$(IGW)/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' > $(LOG_DIR)/crest.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/populate/restaurant' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' | tee -a $(LOG_DIR)/crest.out

# PUT is used for restaurant (update) to update a record
urest:
	echo curl --location --request PUT 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' > $(LOG_DIR)/urest.out
	$(CURL) --location --request PUT 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' --header 'Content-Type: application/json' --data-raw '$(BODY_RESTAURANT)' | tee -a $(LOG_DIR)/urest.out

# DELETE is used with restaurant to delete a record
drest:
	echo curl --location --request DELETE 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/drest.out
	$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/drest.out

# GET is used with restaurant to read a record
rrest:
	echo curl --location --request GET 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rrest.out
	$(CURL) --location --request GET 'http://$(IGW)/api/v1/populate/restaurant/$(RESTAURANT_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rrest.out

#**************************************************** Service  s2  Order****************************************************

# POST is used for order (apipost) to create a new record
corder:
	echo curl --location --request POST 'http://$(IGW)/api/v1/orders/' --header 'Content-Type: application/json' --data-raw '$(BODY_ORDER)' --header '$(TOKEN)' > $(LOG_DIR)/corder.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/orders/' --header 'Content-Type: application/json' --data-raw '$(BODY_ORDER)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/corder.out

# DELETE is used with order to delete a record
dorder:
	echo curl --location --request DELETE 'http://$(IGW)/api/v1/orders/$(ORDER_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/dorder.out
	$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/orders/$(ORDER_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/dorder.out

# GET is used with order to read a record
rorder:
	echo curl --location --request GET 'http://$(IGW)/api/v1/orders/$(ORDER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rorder.out
	$(CURL) --location --request GET 'http://$(IGW)/api/v1/orders/$(ORDER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rorder.out

#**************************************************** Service  s3  Bills****************************************************

# POST is used (apipost) to create a new record
cbills:
	echo curl --location --request POST 'http://$(IGW)/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '$(BODY_BILLS)' --header '$(TOKEN)' > $(LOG_DIR)/cbils.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/bills/' --header 'Content-Type: application/json' --data-raw '$(BODY_BILLS)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/cbills.out

# DELETE is used to delete a record
dbills:
	echo curl --location --request DELETE 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/dbills.out
	$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/dbills.out

# GET is used with bills to read a record
rbills:
	echo curl --location --request GET 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rbills.out
	$(CURL) --location --request GET 'http://$(IGW)/api/v1/bills/$(PAYMENT_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rbills.out

#**************************************************** Service  s4  Discount****************************************************

# GET is used with payment to read discount
rdiscount:
	echo curl --location request GET 'http://$(IGW)/api/v1/discount/show_discount?payment_id=$(PAYMENT_ID)&order_id=$(ORDER_ID)&user_id=$(USER_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rdiscount.out
	$(CURL) --location request GET 'http://$(IGW)/api/v1/discount/show_discount?payment_id=$(PAYMENT_ID)&order_id=$(ORDER_ID)&user_id=$(USER_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rdiscount.out

#**************************************************** Service  s5  Delivery****************************************************

# POST is used (apipost) to create a new record
cdelivery:
	echo curl --location --request POST 'http://$(IGW)/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '$(BODY_DELIVERY)' > $(LOG_DIR)/cdelivery.out
	$(CURL) --location --request POST 'http://$(IGW)/api/v1/delivery/' --header 'Content-Type: application/json' --data-raw '$(BODY_DELIVERY)' | tee -a $(LOG_DIR)/cdelivery.out

# DELETE is used to delete a record
ddelivery:
	echo curl --location --request DELETE 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID2)' --header '$(TOKEN)' > $(LOG_DIR)/ddelivery.out
	$(CURL) --location --request DELETE 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID2)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/ddelivery.out

# GET is to read a record
rdelivery:
	echo curl --location --request GET 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID)' --header '$(TOKEN)' > $(LOG_DIR)/rdelivery.out
	$(CURL) --location --request GET 'http://$(IGW)/api/v1/delivery/$(DELIVERY_ID)' --header '$(TOKEN)' | tee -a $(LOG_DIR)/rdelivery.out

showcontext:
	$(KC) config get-contexts
