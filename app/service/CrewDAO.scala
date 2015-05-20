package service

import controllers.routes
import model.Person

/**
 * Created by damirv on 23/04/15.
 */
class DummyCrewDAO extends CrewDAO {
  var persons: Set[Person] = Set()

  add(Person("Gino", "31638533628", routes.Assets.at("images/avatar-gino.png").url))
  add(Person("Arthur", "31611734558", routes.Assets.at("images/avatar-leon.jpeg").url))
  add(Person("Steven", "31624434821", routes.Assets.at("images/avatar-kevin.jpeg").url))

  override def add(person: Person) = persons += person

  override def get(id: Int): Person = persons.find(_.id == id).get

  override def delete(id: Int) = persons -= get(id)

  override def toString: String = persons.toString
}

trait CrewDAO {
  def persons: Set[Person]

  def add(person: Person): Unit

  def get(id: Int): Person

  def delete(id: Int): Unit
}