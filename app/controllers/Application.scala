package controllers

import model.Nagger
import play.api.mvc._
import play.libs.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listNaggers = Action {
    Ok(views.html.naggers(Naggers.all))
  }

  def addNagger = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    Naggers.add(Nagger(name, phone))
    Ok("Nagger added")
  }

  def updateNagger(id: Int) = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    val nagger = Naggers.get(id.toInt)
    nagger.name = name
    nagger.phone = phone
    Ok("Nagger updated")
  }

  object Naggers {
    var all: Set[Nagger] = Set()

    add(Nagger("Gino", "0612345678"))
    add(Nagger("Leon", ""))

    def add(nagger: Nagger) = all = all + nagger

    def get(id: Int) = all.find(_.id == id).get

    override def toString = all.toString
  }

}