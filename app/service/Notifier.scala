package service

import model.Person

/**
 * Created by steven on 23/04/15.
 */
trait Notifier {
  def notify(person: Person, message: String)
}
