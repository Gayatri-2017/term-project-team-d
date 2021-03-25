"""
SFU CMPT 756
Sample application---user service.
"""

# Standard library modules
import logging
import sys
import time

# Installed packages
from flask import Blueprint
from flask import Flask
from flask import request
from flask import Response

import jwt

from prometheus_flask_exporter import PrometheusMetrics

import requests

import simplejson as json

# The application

app = Flask(__name__)

metrics = PrometheusMetrics(app)
metrics.info('app_info', 'User process')

bp = Blueprint('app', __name__)

db = {
    "name": "http://host.docker.internal:30000/api/v1/datastore",
    "endpoint": [
        "read",
        "write",
        "delete",
        "update"
    ]
}

@bp.route('/', methods=['GET'])
@metrics.do_not_track()
def hello_world():
    return ("If you are reading this in a browser, your service is "
            "operational. Switch to curl/Postman/etc to interact using the "
            "other HTTP verbs.")


@bp.route('/health')
@metrics.do_not_track()
def health():
    return Response("", status=200, mimetype="application/json")


@bp.route('/readiness')
@metrics.do_not_track()
def readiness():
    return Response("", status=200, mimetype="application/json")


#Retrieve payment details based on order id
@bp.route('/<payment_id>', methods=['GET'])
def get_payment(payment_id):

    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(
            json.dumps({"error": "missing auth"}),
            status=401,
            mimetype='application/json')
    payload = {"objtype": "order", "objkey": payment_id}
    url = db['name'] + '/' + db['endpoint'][0]
    response = requests.get(url, params=payload)
    return (response.json())


@bp.route('/', methods=['POST'])
def create_bills():

    #Create a bill.
    #If a record already exists with the same entries,
    #the old UUID is replaced with a new one.
    
    headers = request.headers
     # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}), status=401,
                        mimetype='application/json')
    try:
        content = request.get_json()
        payment_method = content['payment_method']
        discount_applied = content['discount_applied']
        payment_amount = content['payment_amount']
        food_name = content['food_name']
        user_id = content['user_id']
        order_id = content['order_id']
        restaurant_id = content['restaurant_id']

    except Exception:
        return json.dumps({"message": "error reading arguments"})
    url = db['name'] + '/' + db['endpoint'][1]
    response = requests.post(
        url,
        json={"objtype": "payment",
            "payment_method": payment_method,
            "discount_applied": discount_applied,
            "payment_amount":payment_amount,
            "food_name":food_name,
            "user_id":user_id,
            "order_id":order_id,
            "restaurant_id":restaurant_id
            })
    return (response.json())


@bp.route('/<payment_id>', methods=['DELETE'])
def delete_bills(payment_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}),
                        status=401,
                        mimetype='application/json')
    url = db['name'] + '/' + db['endpoint'][2]

    response = requests.delete(url,
                               params={"objtype": "payment", "objkey": payment_id})
    return (response.json())

# All database calls will have this prefix.  Prometheus metric
# calls will not---they will have route '/metrics'.  This is
# the conventional organization.
app.register_blueprint(bp, url_prefix='/api/v1/bills/')

if __name__ == '__main__':
    if len(sys.argv) < 2:
        logging.error("Usage: app.py <service-port>")
        sys.exit(-1)

    p = int(sys.argv[1])
    # Do not set debug=True---that will disable the Prometheus metrics
    app.run(host='0.0.0.0', port=p, threaded=True)
