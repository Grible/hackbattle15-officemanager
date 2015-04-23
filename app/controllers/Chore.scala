package controllers

import play.api.mvc._

/**
 * Created by Gino on 23-04-15.
 */

object Chore extends Controller {
  def add = Action {
    Ok(views.html.chore("Your new application is ready."))
  }

}
