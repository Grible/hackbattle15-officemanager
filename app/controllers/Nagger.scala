package controllers

import controllers.Application._
import play.api.mvc._

/**
 * Created by steven on 23/04/15.
 */
object Nagger extends Controller {
  def addNagger = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    Naggers.add(model.Nagger(name, phone))
    Redirect(routes.Nagger.listNaggers())
  }

  def listNaggers = Action {
    Ok(views.html.naggers(Naggers.all))
  }

  def updateNagger(id:Int) = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    val nagger = Naggers.get(id.toInt)
    nagger.name = name
    nagger.phone = phone
    Redirect(routes.Nagger.listNaggers())
  }

  def deleteNagger(id:Int) = Action {
    Naggers.delete(id)
    Redirect(routes.Nagger.listNaggers())
  }

  object Naggers {
    var all: Set[model.Nagger] = Set()

    add(model.Nagger("Gino", "0612345678"))
    add(model.Nagger("Leon", ""))

    def add(nagger: model.Nagger) = all = all + nagger
    def get(id:Int) = all.find(_.id == id).get
    def delete(id:Int) = all = all - get(id)

    override def toString = all.toString
  }

}
