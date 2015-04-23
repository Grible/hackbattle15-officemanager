package controllers

import play.api.mvc._

object Chore extends Controller {
  def add = Action {
    Ok(views.html.chore("Your new application is ready."))
  }

}
