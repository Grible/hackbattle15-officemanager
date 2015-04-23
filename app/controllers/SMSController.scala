package controllers

import java.net.{URLEncoder, URLDecoder}

import play.Play
import play.api.mvc._

/**
 * Created by Arthur on 23-04-15.
 */

object SMSController extends Controller {

  def send(mobilenumber:String, smstext:String) = Action { request =>

    val apiurl = Play.application().configuration().getString("nexmo.api.url")
    val apikey = Play.application().configuration().getString("nexmo.api.key")
    val apisecret = Play.application().configuration().getString("nexmo.api.secret")
    val sender = Play.application().configuration().getString("nexmo.sender")
    val safetext=URLEncoder.encode(smstext, "UTF-8")
    val safenumber = URLEncoder.encode(mobilenumber,  "UTF-8")

    val url = apiurl.toString + "api_key=" + apikey + "&api_secret=" + apisecret + "&from=" + sender +"&to="+ safenumber + "&text=" + safetext
    println("send sms: " + url)
    val result = scala.io.Source.fromURL(url).mkString

    println(result)

    Ok(views.html.sms("SMS Send to "+ mobilenumber))
  }

}
