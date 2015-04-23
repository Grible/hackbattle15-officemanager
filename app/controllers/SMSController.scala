package controllers

import play.Play
import play.api.mvc._

/**
 * Created by Arthur on 23-04-15.
 */

object SMSController extends Controller {
  def send = Action {

    val apiurl = Play.application().configuration().getString("nexmo.api.url")
    val apikey = Play.application().configuration().getString("nexmo.api.key")
    val apisecret = Play.application().configuration().getString("nexmo.api.secret")
    val sender = Play.application().configuration().getString("nexmo.sender")
    val mobilenumber = "31611734558"
    val smstext="Greetings%20from%20TNW%20Hack%20Batttle%202015"

    val url = apiurl.toString + "api_key=" + apikey + "&api_secret=" + apisecret + "&from=" +sender +"&to="+ mobilenumber + "&text=" + smstext

    val result = scala.io.Source.fromURL(url).mkString

    println(result)

    Ok(views.html.sms("SMS Send to "))
  }

}
