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


#updates the record in user table

@bp.route('/user/<user_id>', methods=['PUT'])
def update_user(user_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}), status=401,
                        mimetype='application/json')
    try:
        content = request.get_json()
        user_name = content['user_name']
        user_email = content['user_email']
        user_phone = content['user_phone']
    except Exception:
        return json.dumps({"message": "error reading arguments"})
    url = db['name'] + '/' + db['endpoint'][3]
    response = requests.put(
        url,
        params={"objtype": "user", "objkey": user_id},
        json={"user_name": user_name, "user_email": user_email, "user_phone": user_phone})
    return (response.json())


@bp.route('/user', methods=['POST'])
def create_user():
    """
    Create a user.
    If a record already exists with the same user_name, user_email, and user_phone,
    the old UUID is replaced with a new one.
    """
    try:
        content = request.get_json()
        user_name = content['user_name']
        user_email = content['user_email']
        user_phone = content['user_phone']
    except Exception:
        return json.dumps({"message": "error reading arguments"})
    url = db['name'] + '/' + db['endpoint'][1]
    response = requests.post(
        url,
        json={"objtype": "user",
              "user_name": user_name,
              "user_email": user_email,
              "user_phone": user_phone})
    return (response.json())


#delets the record in user table

@bp.route('/user/<user_id>', methods=['DELETE'])
def delete_user(user_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}),
                        status=401,
                        mimetype='application/json')
    url = db['name'] + '/' + db['endpoint'][2]
    response = requests.delete(url,
                               params={"objtype": "user", "objkey": user_id})
    return (response.json())


#gets the record in user table

@bp.route('/user/<user_id>', methods=['GET'])
def get_user(user_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(
            json.dumps({"error": "missing auth"}),
            status=401,
            mimetype='application/json')  
    payload = {"objtype": "user", "objkey": user_id}
    url = db['name'] + '/' + db['endpoint'][0]
    response = requests.get(url, params=payload)
    return (response.json())


@bp.route('/login', methods=['PUT'])
def login():
    try:
        content = request.get_json()
        user_id = content['user_id']
    except Exception:
        return json.dumps({"message": "error reading parameters"})
    url = db['name'] + '/' + db['endpoint'][0]
    response = requests.get(url, params={"objtype": "user", "objkey": user_id})
    data = response.json()
    if len(data['Items']) > 0:
        encoded = jwt.encode({'user_id': user_id, 'time': time.time()},
                             'secret',
                             algorithm='HS256')
    return encoded


@bp.route('/logoff', methods=['PUT'])
def logoff():
    try:
        content = request.get_json()
        _ = content['jwt']
    except Exception:
        return json.dumps({"message": "error reading parameters"})
    return {}


#updates the record in restaurant table

@bp.route('/restaurant/<restaurant_id>', methods=['PUT'])
def update_restaurant(restaurant_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}), status=401,
                        mimetype='application/json')
    try:
        content = request.get_json()
        restaurant_name = content['restaurant_name']
        food_name = content['food_name']
        food_price = content['food_price']
    except Exception:
        return json.dumps({"message": "error reading arguments"})
    url = db['name'] + '/' + db['endpoint'][3]
    response = requests.put(
        url,
        params={"objtype": "restaurant", "objkey": restaurant_id},
        json={"restaurant_name": restaurant_name, "food_name": food_name, "food_price": food_price})
    return (response.json())




@bp.route('/restaurant', methods=['POST'])
def create_restaurant():
    """
    create restaurant
    If a record already exists with the same restaurant_name, food_name and food_price,
    the old UUID is replaced with a new one.
    """
    try:
        content = request.get_json()
        restaurant_name = content['restaurant_name']
        food_name = content['food_name']
        food_price = content['food_price']
    except Exception:
        return json.dumps({"message": "error reading arguments"})
    url = db['name'] + '/' + db['endpoint'][1]
    response = requests.post(
        url,
        json={"objtype": "restaurant",
              "restaurant_name":restaurant_name,
              "food_name": food_name,
              "food_price": food_price,
              })
    return (response.json())



#delets the record in restaurant table
@bp.route('/restaurant/<restaurant_id>', methods=['DELETE'])
def delete_restaurant(restaurant_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(json.dumps({"error": "missing auth"}),
                        status=401,
                        mimetype='application/json')
    url = db['name'] + '/' + db['endpoint'][2]
    
    response = requests.delete(url,
                               params={"objtype": "restaurant", "objkey": restaurant_id})
    return (response.json())


#gets the record in restaurant table
@bp.route('/restaurant/<restaurant_id>', methods=['GET'])
def get_restaurant(restaurant_id):
    headers = request.headers
    # check header here
    if 'Authorization' not in headers:
        return Response(
            json.dumps({"error": "missing auth"}),
            status=401,
            mimetype='application/json')  

    payload = {"objtype": "restaurant", "objkey": restaurant_id}
    url = db['name'] + '/' + db['endpoint'][0]
    response = requests.get(url, params=payload)
    return (response.json())



# All database calls will have this prefix.  Prometheus metric
# calls will not---they will have route '/metrics'.  This is
# the conventional organization.
app.register_blueprint(bp, url_prefix='/api/v1/populate/')

if __name__ == '__main__':
    if len(sys.argv) < 2:
        logging.error("Usage: app.py <service-port>")
        sys.exit(-1)

    p = int(sys.argv[1])
    # Do not set debug=True---that will disable the Prometheus metrics
    app.run(host='0.0.0.0', port=p, threaded=True)
