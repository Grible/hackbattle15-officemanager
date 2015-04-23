package service

import java.net.URLEncoder

import model.Person
import play.Play
import play.api.Logger

class NexmoNotifier extends Notifier {
  val logger = Logger("NexmoNotifier")
  val apiurl = Play.application().configuration().getString("nexmo.api.url")
  val apikey = Play.application().configuration().getString("nexmo.api.key")
  val apisecret = Play.application().configuration().getString("nexmo.api.secret")
  val sender = Play.application().configuration().getString("nexmo.sender")

  override def notify(person: Person, text: String): Unit = {
    val phone = person.phone
    val url = apiurl.toString + "api_key=" + apikey + "&api_secret=" + apisecret + "&from=" + sender + "&to=" + phone + "&text=" + URLEncoder.encode(text, "UTF-8")
    logger.info("send sms: " + url)
    scala.io.Source.fromURL(url).mkString
  }

}

class ConsoleNotifier extends Notifier {
  val logger = Logger("ConsoleNotifier")

  override def notify(person: Person, text: String): Unit = logger.info(s"Message to ${person.name}: '$text'")
}

trait Notifier {
  def notify(person: Person, text: String): Unit
}
