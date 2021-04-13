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

object User {
  val feeder = csv("user.csv").eager.circular
  val user_coverage = {
    feed(feeder)
      .exec(http("Create User")
        .post("/api/v1/populate/user")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "user_name": "${user_name}",
          "user_email": "${user_email}",
          "user_phone": "${user_phone}"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200))
        .check(jsonPath("$..user_id").ofType[String].saveAs("user_id")))
      .pause(1)
      .exec(http("Login User ${user_id}")
        .put("/api/v1/populate/user/login")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "user_id": "${user_id}" }
          """ ))
        .check(bodyString.saveAs("ResponseTokenLogin")))
      .pause(1)
      .exec(http("Read User ${user_id}")
        .get("/api/v1/populate/user/${user_id}")
        .header("Authorization" , StringBody("""Bearer ${ResponseTokenLogin}""")))
      .pause(1)
      .exec(http("Update User ${user_id}")
        .put("/api/v1/populate/user/${user_id}")
        .header("Content-Type" , "application/json")
        .header("Authorization" , StringBody("""Bearer ${ResponseTokenLogin}"""))
        .body(StringBody(string = """{
          "user_name": "Sherlock",
          "user_email": "sherlock@gmail.com",
          "user_phone": "1234567890"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
      .pause(1)
      .exec(http("LogOff User ${user_id}")
        .put("/api/v1/populate/user/logoff")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{   "jwt": "${ResponseTokenLogin}" }""" ))
        .check(bodyString.saveAs("ResponseTokenLogoff")))
      .pause(1)
      .exec(http("Delete User ${user_id}")
        .delete("/api/v1/populate/user/${user_id}")
        .header("Authorization" , StringBody("""Bearer ${ResponseTokenLogin}"""))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
  }
}



object Restaurant {
  val feeder = csv("restaurant.csv").eager.circular
  val restaurant_coverage = {
    feed(feeder)
      .exec(http("Create Restaurant")
        .post("/api/v1/populate/restaurant")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "restaurant_name": "${restaurant_name}",
          "food_name": "${food_name}",
          "food_price": "${food_price}"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200))
        .check(jsonPath("$..restaurant_id").ofType[String].saveAs("restaurant_id")))
      .pause(1)
      .exec(http("Read Restaurant ${restaurant_id}")
        .get("/api/v1/populate/restaurant/${restaurant_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}""")))
      .pause(1)
      .exec(http("Update Restaurant ${restaurant_id}")
        .put("/api/v1/populate/restaurant/${restaurant_id}")
        .header("Content-Type" , "application/json")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}"""))
        .body(StringBody(string = """{
          "restaurant_name": "Tandoori Flame",
          "food_name": "Masala Dosa",
          "food_price": "20"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
      .pause(1)
      .exec(http("Delete Restaurant ${restaurant_id}")
        .delete("/api/v1/populate/restaurant/${restaurant_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}"""))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
  }
}


object Orders {
  val feeder = csv("orders.csv").eager.circular
  val orders_coverage = {
    feed(feeder)
      .exec(http("Create Orders")
        .post("/api/v1/orders/")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "user_id": "${user_id}",
          "restaurant_id": "${restaurant_id}",
          "food_name": "${food_name}"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200))
        .check(jsonPath("$..order_id").ofType[String].saveAs("order_id")))
      .pause(1)
      .exec(http("Read Orders ${order_id}")
        .get("/api/v1/orders/${order_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}""")))
      .pause(1)
      .exec(http("Delete Orders ${order_id}")
        .delete("/api/v1/orders/${order_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}"""))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
  }
}


object Bills {
  val feeder = csv("bills.csv").eager.circular
  val bills_coverage = {
    feed(feeder)
      .exec(http("Create Payment")
        .post("/api/v1/bills/")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "payment_method": "${payment_method}",
          "discount_applied": "${discount_applied}",
          "payment_amount": "${discount_applied}",
          "user_id": "${user_id}",
          "order_id": "${order_id}",
          "restaurant_id": "${restaurant_id}",
          "food_name": "${food_name}"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200))
        .check(jsonPath("$..payment_id").ofType[String].saveAs("payment_id")))
      .pause(1)
      .exec(http("Read Payment ${payment_id}")
        .get("/api/v1/bills/${payment_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}""")))
      .pause(1)
      .exec(http("Delete Payment ${payment_id}")
        .delete("/api/v1/bills/${payment_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}"""))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
  }
}


object Discount {
  val feeder = csv("discount.csv").eager.circular
  val discount_coverage = {
    feed(feeder)
      .exec(http("Get Discount")
        .get("/api/v1/discount/show_discount?payment_id=${payment_id}&order_id=${order_id}&user_id=${user_id}"))
      .pause(1)
  }
}


object Delivery {
  val feeder = csv("delivery.csv").eager.circular
  val delivery_coverage = {
    feed(feeder)
      .exec(http("Create Delivery")
        .post("/api/v1/delivery/")
        .header("Content-Type" , "application/json")
        .body(StringBody(string = """{
          "driver_name": "${driver_name}",
          "predicted_delivery_time": "${predicted_delivery_time}",
          "order_id": "${order_id}"}
          """ ))
        .check(status.not(404), status.not(500))
        .check(status.is(200))
        .check(jsonPath("$..delivery_id").ofType[String].saveAs("delivery_id")))
      .pause(1)
      .exec(http("Read Delivery ${delivery_id}")
        .get("/api/v1/delivery/${delivery_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}""")))
      .pause(1)
      .exec(http("Delete Delivery ${delivery_id}")
        .delete("/api/v1/delivery/${delivery_id}")
        .header("Authorization" , StringBody("""Bearer {ResponseTokenLogin}"""))
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
  }
}


// Get Cluster IP from CLUSTER_IP environment variable or default to 127.0.0.1 (Minikube)
class ReadTablesSim extends Simulation {
  val httpProtocol = http
    .baseUrl("http://" + Utility.envVar("CLUSTER_IP", "127.0.0.1") + "/")
    .acceptHeader("application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .authorizationHeader("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZGJmYmMxYzAtMDc4My00ZWQ3LTlkNzgtMDhhYTRhMGNkYTAyIiwidGltZSI6MTYwNzM2NTU0NC42NzIwNTIxfQ.zL4i58j62q8mGUo5a0SQ7MHfukBUel8yl8jGT5XmBPo")
    .acceptLanguageHeader("en-US,en;q=0.5")
}


class CoverageUserSim extends ReadTablesSim {
  val scnCoverageUser = scenario("Populate User Coverage Test")
    .exec(User.user_coverage)
  setUp(
    scnCoverageUser.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class CoverageRestaurantSim extends ReadTablesSim {
  val scnCoverageRestaurant = scenario("Populate Restaurant Coverage Test")
    .exec(Restaurant.restaurant_coverage)
  setUp(
    scnCoverageRestaurant.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class CoverageOrdersSim extends ReadTablesSim {
  val scnCoverageOrders = scenario("Orders Coverage Test")
    .exec(Orders.orders_coverage)
  setUp(
    scnCoverageOrders.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class CoverageBillsSim extends ReadTablesSim {
  val scnCoverageBills = scenario("Bills Coverage Test")
    .exec(Bills.bills_coverage)
  setUp(
    scnCoverageBills.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class CoverageDiscountSim extends ReadTablesSim {
  val scnCoverageDiscount = scenario("Discount Coverage Test")
    .exec(Discount.discount_coverage)
  setUp(
    scnCoverageDiscount.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class CoverageDeliverySim extends ReadTablesSim {
  val scnCoverageDelivery = scenario("ReadDelivery")
    .exec(Delivery.delivery_coverage)
  setUp(
    scnCoverageDelivery.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}
