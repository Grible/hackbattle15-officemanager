package dao

import controllers.routes
import model.Person

/**
 * Created by damirv on 23/04/15.
 */
class InMemoryPersonDAO extends PersonDAO {
  private var _persons: Seq[Person] = Seq()

  add(Person("Gino", "31638533628", routes.Assets.at("images/avatar-gino.png").url))
  add(Person("Arthur", "31611734558", routes.Assets.at("images/avatar-leon.jpeg").url))
  add(Person("Steven", "31624434821", routes.Assets.at("images/avatar-kevin.jpeg").url))

  override def add(person: Person) = _persons = _persons.+:(person)

  override def get(id: String) = _persons.find(_.id == id)

  override def delete(id: String) =  {
    get(id) match {
      case Some(p: Person) => {
        _persons = _persons.filterNot(_ == p)
        Right(p)
      }
      case None => Left("Could not delete person, person does not exist")
    }
  }

  override def persons: Seq[Person] = _persons

  override def toString: String = _persons.toString
}

trait PersonDAO {
  def persons: Seq[Person]

  def add(person: Person): Unit

  def get(id: String): Option[Person]

  def delete(id: String): Either[String, Person]
}