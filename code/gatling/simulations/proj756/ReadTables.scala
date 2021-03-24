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


object RUser {
  val feeder = csv("user.csv").eager.random
  val ruser = forever("i") {
    feed(feeder)
    .exec(http("RUser ${i}")
      .get("/api/v1/populate/user/${UUID}"))
      .pause(1)
  }
}


object RRestaurant {
  val feeder = csv("restaurant.csv").eager.random
  val rrestaurant = forever("i") {
    feed(feeder)
    .exec(http("RRestaurant ${i}")
      .get("/api/v1/populate/restaurant/${UUID}"))
      .pause(1)
  }
}


object ROrders {
  val feeder = csv("orders.csv").eager.random
  val rorders = forever("i") {
    feed(feeder)
    .exec(http("ROrders ${i}")
      .get("/api/v1/orders/${UUID}"))
      .pause(1)
  }
}


object RBills {
  val feeder = csv("bills.csv").eager.random
  val rbills = forever("i") {
    feed(feeder)
    .exec(http("RBills ${i}")
      .get("/api/v1/bills/${UUID}"))
      .pause(1)
  }
}


object RDiscount {
  val feeder = csv("discount.csv").eager.random
  val rdiscount = forever("i") {
    feed(feeder)
    .exec(http("RDiscount ${i}")
      .get("/api/v1/discount/${UUID}"))
      .pause(1)
  }
}


object RDelivery {
  val feeder = csv("delivery.csv").eager.random
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


class ReadUserSim extends ReadTablesSim {
  val scnReadUser = scenario("ReadUser")
      .exec(RUser.ruser)
  setUp(
    scnReadUser.inject(atOnceUsers(Utility.envVarToInt("USERS", 1)))
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