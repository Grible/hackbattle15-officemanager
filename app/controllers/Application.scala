package controllers

import model.Nagger
import play.api.mvc._
import play.libs.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }



}