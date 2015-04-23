package controllers

import controllers.Application._
import model.Nagger
import play.api.mvc._

/**
 * Created by steven on 23/04/15.
 */
object NaggerController extends Controller {
  val listNaggersRoute = routes.NaggerController.listNaggers()

  def addNagger = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val phone = request.body.asFormUrlEncoded.get.get("phone").get.head
    Naggers.add(Nagger(name, phone))
    Redirect(listNaggersRoute)
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
    Redirect(listNaggersRoute)
  }

  def deleteNagger(id:Int) = Action {
    Naggers.delete(id)
    Redirect(listNaggersRoute)
  }

  object Naggers {
    var all: Set[Nagger] = Set()

    add(Nagger("Gino", "0612345678"))
    add(Nagger("Leon", ""))

    def add(nagger: Nagger) = all = all + nagger
    def get(id:Int) = all.find(_.id == id).get
    def delete(id:Int) = all = all - get(id)

    override def toString = all.toString
  }

}
