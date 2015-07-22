package controllers

import dao.PersonDAO
import model.Person
import play.api.mvc._
import scaldi.Injectable._
import scaldi.Injector

/**
 * Created by steven on 23/04/15.
 */
class PersonController(implicit inj: Injector) extends Controller {
  var crewDAO = inject[PersonDAO]
//  val showCrewRoute = routes.CrewController.showCrew()

//  def addPerson = Action { request =>
//    val name = request.body.asFormUrlEncoded.get.get("name").get.head
//    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
//    crewDAO.add(Person(name, phone))
//    Redirect(showCrewRoute)
//  }
//
//  def showCrew = Action {
//    Ok(views.html.persons(crewDAO.persons))
//  }
//
//  def updatePerson(id: Int) = Action { request =>
//    val name = request.body.asFormUrlEncoded.get.get("name").get.head
//    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
//    val nagger = crewDAO.get(id.toInt)
//    nagger.name = name
//    nagger.phone = phone
//    Redirect(showCrewRoute)
//  }
//
//  def deletePerson(id: Int) = Action {
//    crewDAO.delete(id)
//    Redirect(showCrewRoute)
//  }

}
