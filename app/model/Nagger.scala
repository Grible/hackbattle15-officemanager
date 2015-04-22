package model

import controllers.{Assets, routes}

/**
 * Created by steven on 22/04/15.
 */
case class Nagger(name: String, phone:String) {
  var avatarSrc:String = routes.Assets.at("images/favicon.png").url
}
