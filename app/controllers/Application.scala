package controllers

import model.Nagger
import play.api.mvc._
import play.libs.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listNaggers = Action {
    val naggerJson = Naggers.toString
    Ok(naggerJson)
  }


  object Naggers {
    var all: Set[Nagger] = Set()

    add(Nagger("Gino", "0612345678"))
    add(Nagger("Leon", ""))

    def add(nagger: Nagger) = all = all + nagger

    override def toString = all.toString
  }

}