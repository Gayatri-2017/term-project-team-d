package proj756

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.io.File

object Utility {
  /*
    Utility to get an Int from an environment variable.
    Return defInt if the environment var does not exist
    or cannot be converted to a string.
  */
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
        .get("/api/v1/populate/user/${user_id}"))
      .pause(1)
      .exec(http("Update User ${user_id}")
        .put("/api/v1/populate/user/${user_id}")
        .header("Content-Type" , "application/json")
        .header("authorization", "ResponseTokenLogin")
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
        .header("authorization", "ResponseTokenLogin")
        .check(status.not(404), status.not(500))
        .check(status.is(200)))
    }
}



object RRestaurant {
  val feeder = csv("restaurant.csv").eager.circular
  val rrestaurant = forever("i") {
    feed(feeder)
    .exec(http("RRestaurant ${i}")
      .get("/api/v1/populate/restaurant/${UUID}"))
      .pause(1)
  }
}


object ROrders {
  val feeder = csv("orders.csv").eager.circular
  val rorders = forever("i") {
    feed(feeder)
    .exec(http("ROrders ${i}")
      .get("/api/v1/orders/${UUID}"))
      .pause(1)
  }
}


object RBills {
  val feeder = csv("bills.csv").eager.circular
  val rbills = forever("i") {
    feed(feeder)
    .exec(http("RBills ${i}")
      .get("/api/v1/bills/${UUID}"))
      .pause(1)
  }
}


object RDiscount {
  val feeder = csv("discount.csv").eager.circular
  val rdiscount = forever("i") {
    feed(feeder)
    .exec(http("RDiscount ${i}")
      .get("/api/v1/discount/show_discount?payment_id=${payment_id}&order_id=${order_id}&user_id=${user_id}"))
      .pause(1)
  }
}


object RDelivery {
  val feeder = csv("delivery.csv").eager.circular
  val rdelivery = forever("i") {
    feed(feeder)
    .exec(http("RDelivery ${i}")
      .get("/api/v1/delivery/${UUID}"))
      .pause(1)
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
  val scnCoverageUser = scenario("User Coverage Test")
      .exec(User.user_coverage)
  setUp(
    scnCoverageUser.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class ReadRestaurantSim extends ReadTablesSim {
  val scnReadRestaurant = scenario("ReadRestaurant")
      .exec(RRestaurant.rrestaurant)
  setUp(
    scnReadRestaurant.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class ReadOrdersSim extends ReadTablesSim {
  val scnReadOrders = scenario("ReadOrders")
      .exec(ROrders.rorders)
  setUp(
    scnReadOrders.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class ReadBillsSim extends ReadTablesSim {
  val scnReadBills = scenario("ReadBills")
      .exec(RBills.rbills)
  setUp(
    scnReadBills.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class ReadDiscountSim extends ReadTablesSim {
  val scnReadDiscount = scenario("ReadDiscount")
      .exec(RDiscount.rdiscount)
  setUp(
    scnReadDiscount.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}


class ReadDeliverySim extends ReadTablesSim {
  val scnReadDelivery = scenario("ReadDelivery")
      .exec(RDelivery.rdelivery)
  setUp(
    scnReadDelivery.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
  ).protocols(httpProtocol)
}
