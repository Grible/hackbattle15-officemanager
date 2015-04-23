package service

import java.net.URLEncoder

import play.Play

/**
 * Created by @arthurarts on 23-04-15.
 */
object SMSSender {

    val apiurl = Play.application().configuration().getString("nexmo.api.url")
    val apikey = Play.application().configuration().getString("nexmo.api.key")
    val apisecret = Play.application().configuration().getString("nexmo.api.secret")
    val sender = Play.application().configuration().getString("nexmo.sender")

  def sendSms(mobileNumber: String, text: String): String = {

    val url = apiurl.toString + "api_key=" + apikey + "&api_secret=" + apisecret + "&from=" + sender +"&to="+ mobileNumber + "&text=" + URLEncoder.encode(text, "UTF-8")
    println("send sms: " + url)
    scala.io.Source.fromURL(url).mkString
  }

}
