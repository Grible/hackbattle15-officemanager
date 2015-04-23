package controllers

import model.Person
import play.api.mvc._

/**
 * Created by steven on 23/04/15.
 */
object CrewController extends Controller {
  val showCrewRoute = routes.CrewController.showCrew()

  def addPerson = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    Crew.add(Person(name, phone))
    Redirect(showCrewRoute)
  }

  def showCrew = Action {
    Ok(views.html.crew(Crew.persons))
  }

  def updatePerson(id: Int) = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    val nagger = Crew.get(id.toInt)
    nagger.name = name
    nagger.phone = phone
    Redirect(showCrewRoute)
  }

  def deletePerson(id: Int) = Action {
    Crew.delete(id)
    Redirect(showCrewRoute)
  }

  object Crew {
    var persons: Set[Person] = Set()

    add(Person("Gino", "31638533628"))
    add(Person("Arthur", "31611734558"))
    add(Person("Steven", "31624434821"))

    def add(person: Person) = persons = persons + person

    def get(id: Int) = persons.find(_.id == id).get

    def delete(id: Int) = persons = persons - get(id)

    override def toString = persons.toString
  }

}
