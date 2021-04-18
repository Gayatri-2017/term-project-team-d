package proj756

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.io.File
import scala.util.Random

object Utility {
  /*
    Utility to get an Int from an environment variable.
    Return defInt if the environment var does not exist
    or cannot be converted to a string.
  */
  val initSession = exec(flushCookieJar)

  def envVarToInt(ev: String, defInt: Int): Int = {
    try {
      sys.env(ev).toInt
    } catch {
      case e: Exception => defInt
    }
  }

  /*
    Utility to get an environment variable.
    Return defStr if the environment var does not exist.
  */
  def envVar(ev: String, defStr: String): String = {
    sys.env.getOrElse(ev, defStr)
  }
}

object UserloginTest {
  val feeder = csv("data/user_login.csv").eager.circular
  val login_load = {
    feed(feeder)
      .exec(http("Login Load simulation Scenerio ${user_id}")
        .put("/api/v1/populate/login")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
                        "user_id": "${user_id}" }
                        """ ))
        .check(status.is(200))
        .check(bodyString.saveAs("loginToken")))
      .pause(2)
      .exec(http("Logoff after just browsing sometime in the application") 
        .put("/api/v1/populate/logoff")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{   "jwt": "${loginToken}" }""" ))
        .check(status.is(200)))
      .pause(2)
    }
}

object LoadReadRestaurantTest {
  val feeder = csv("data/user_login_restaurant.csv").eager.circular
  val read_restaurant_load = {
    feed(feeder)
      .exec(http("Browse Restaurant Load simulation Scenerio - login")
      .put("/api/v1/populate/login")
      .header("Content-Type" , "application/json")
      .body(StringBody(string = """{
          "user_id": "${user_id}" }
          """ ))
      .check(status.is(200))
      .check(bodyString.saveAs("loginToken")))
      .pause(2) 
      .exec(http("Read Restaurant ${restaurant_id}")
        .get("/api/v1/populate/restaurant/${restaurant_id}")
        .header("Authorization" , StringBody( """Bearer {ResponseTokenLogin}""" )))
      .pause(2)
      .exec(http("Logoff application")
      .put("/api/v1/populate/logoff")
      .header("Content-Type" , "application/json")
      .body(StringBody(string = """{   "jwt": "${loginToken}" }""" ))
      .check(status.is(200)))
      .pause(2)
    }
}

object LoadUserTest {
  val feeder = csv("data/user_load_values.csv").eager.circular
  val user_load = {
    feed(feeder)
      .exec(http("Load Test Simulation")
        .post("/api/v1/populate/user")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "user_name": "${user_name}",
          "user_email": "${user_email}",
          "user_phone": "${user_phone}"}
          """ ))
        .check(status.is(200))
        .check(jsonPath("$..user_id").ofType[String].saveAs("userId")))
	  .pause(2)	  
      .exec(http("Login User ${userId}")
        .put("/api/v1/populate/login")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "user_id": "${userId}" }
          """ ))
        .check(status.is(200))
        .check(bodyString.saveAs("loginToken")))
	  .pause(2)	  
      .exec(http("Create new order")
        .post("/api/v1/orders/")
        .header("Content-Type" , "application/json")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .body(StringBody(string = """{
          "user_id": "${userId}",
          "restaurant_id": "${restaurant_id}",
          "food_name": "${food_name}"}
          """ ))
        .check(status.is(200))
        .check(jsonPath("$..order_id").ofType[String].saveAs("orderId")))
	  .pause(2)
      .exec(http("Review order ${orderId}")
        .get("/api/v1/orders/${orderId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}""")))
	  .pause(2)
      .exec(http("Add payment for submitted order ${orderId}")
        .post("/api/v1/bills/")
        .header("Content-Type" , "application/json")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .body(StringBody(string = """{
          "payment_method": "${payment_method}",
          "discount_applied": "${discount_applied}",
          "payment_amount": "${payment_amount}",
          "user_id": "${userId}",
          "order_id": "${orderId}",
          "restaurant_id": "${restaurant_id}",
          "food_name": "${food_name}"}
          """ ))
        .check(status.is(200))
        .check(jsonPath("$..payment_id").ofType[String].saveAs("paymentId")))
	  .pause(2)
      .exec(http("Review payment ${paymentId}")
        .get("/api/v1/bills/${paymentId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Review Discount ${paymentId}")
        .get("/api/v1/discount/show_discount?payment_id=${paymentId}&order_id=${orderId}&user_id=${userId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Add delivery details for order ${orderId}")
        .post("/api/v1/delivery/")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "driver_name": "${driver_name}",
          "predicted_delivery_time": "${predicted_delivery_time}",
          "order_id": "${orderId}"}
          """ ))
        .check(status.is(200))
        .check(jsonPath("$..delivery_id").ofType[String].saveAs("deliveryId")))
	  .pause(2)	  
      .exec(http("LogOff User ${userId}")
        .put("/api/v1/populate/logoff")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{   "jwt": "${loginToken}" }""" ))
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Delete Delivery ${deliveryId}")
        .delete("/api/v1/delivery/${deliveryId}")
        .header("authorization", "ResponseTokenLogin")
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Delete Payment ${paymentId}")
        .delete("/api/v1/bills/${paymentId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Delete Order ${orderId}")
        .delete("/api/v1/orders/${orderId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .check(status.is(200)))
	  .pause(2)
      .exec(http("Delete User ${userId}")
        .delete("/api/v1/populate/user/${userId}")
        .header("Authorization" , StringBody("""Bearer ${loginToken}"""))
        .check(status.is(200)))
  }
}

// Get Cluster IP from CLUSTER_IP environment variable or default to 127.0.0.1 (Minikube)
class ReadTablesSim extends Simulation {
  val httpProtocol = http
    //.baseUrl("http://" + Utility.envVar("CLUSTER_IP", "127.0.0.1") + "/")
    .baseUrl("http://20.151.40.207/")
    .acceptHeader("application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .authorizationHeader("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZGJmYmMxYzAtMDc4My00ZWQ3LTlkNzgtMDhhYTRhMGNkYTAyIiwidGltZSI6MTYwNzM2NTU0NC42NzIwNTIxfQ.zL4i58j62q8mGUo5a0SQ7MHfukBUel8yl8jGT5XmBPo")
    .acceptLanguageHeader("en-US,en;q=0.5")
}

class LoadUserSim extends ReadTablesSim {
  val scnLoadUser = scenario("Populate User Load Test")
    .exec(UserloginTest.login_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadReadRestaurantTest.read_restaurant_load)
    .pause(2)
    .exec(LoadUserTest.user_load)
  setUp(
    scnLoadUser.inject(constantConcurrentUsers(25).during(30.minutes))
  ).protocols(httpProtocol)
}
