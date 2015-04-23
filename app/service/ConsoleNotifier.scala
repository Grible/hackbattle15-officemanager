package service

import model.Person

/**
 * Created by steven on 24/04/15.
 */
object ConsoleNotifier extends Notifier {
  override def notify(person: Person, message: String): Unit = {
    val name = person.name
    println(s"Message to $name: $message")
  }
}
