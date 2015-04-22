package model

import controllers.{Assets, routes}

/**
 * Created by steven on 22/04/15.
 */

class Nagger(_name: String, _phone: String) {
  var avatarSrc: String = routes.Assets.at("images/favicon.png").url
  var id: Int = IDGen.get
  var name = _name
  var phone = _phone
}

object Nagger {
  def apply(name: String, phone: String) = new Nagger(name, phone)
}

object IDGen {
  var idCount: Int = 0

  def get = {
    idCount += 1
    idCount - 1
  }
}
