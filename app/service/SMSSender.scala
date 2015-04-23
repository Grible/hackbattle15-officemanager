package service

import java.net.URLEncoder

import play.Play
import play.api.Logger

class NexmoSMSSender extends SMSSender {
  val logger = Logger("NexmoSMSSender")
  val apiurl = Play.application().configuration().getString("nexmo.api.url")
  val apikey = Play.application().configuration().getString("nexmo.api.key")
  val apisecret = Play.application().configuration().getString("nexmo.api.secret")
  val sender = Play.application().configuration().getString("nexmo.sender")

  override def sendSms(mobileNumber: String, text: String): Unit = {
    val url = apiurl.toString + "api_key=" + apikey + "&api_secret=" + apisecret + "&from=" + sender + "&to=" + mobileNumber + "&text=" + URLEncoder.encode(text, "UTF-8")
    logger.info("send sms: " + url)
    scala.io.Source.fromURL(url).mkString
  }

}

class DummySMSSender extends SMSSender {
  val logger = Logger("DummySMSSender")

  override def sendSms(mobileNumber: String, text: String): Unit = logger.info(s"send sms: [mobile = $mobileNumber, text= '$text']")
}

trait SMSSender {
  def sendSms(mobileNumber: String, text: String): Unit
}
