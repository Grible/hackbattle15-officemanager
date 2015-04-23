package controllers

import java.net.{URLEncoder, URLDecoder}

import play.Play
import play.api.mvc._
import service._
/**
 * Created by Arthur on 23-04-15.
 */

object SMSController extends Controller {

  def send(mobilenumber:String, smstext:String) = Action { request =>

    SMSSender.sendSms(mobilenumber, smstext)

    Ok(views.html.sms("SMS Send to "+ mobilenumber))
  }

}
