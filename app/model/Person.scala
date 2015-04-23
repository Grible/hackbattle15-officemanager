package model

import controllers.routes

/**
 * Created by steven on 22/04/15.
 */

class Person(_name: String, _phone: String, _avatarSrc: String) {
  var id: Int = IDGen.get
  var name = _name
  var phone = _phone
  var avatarSrc = _avatarSrc
}

object Person {
  def apply(name: String, phone: String) = {
    val avatarSrc: String = routes.Assets.at("images/favicon.png").url
    new Person(name, phone, avatarSrc)
  }

  def apply(name: String, phone: String, avatarSrc: String) = {
    new Person(name, phone, avatarSrc)
  }
}

object IDGen {
  var idCount: Int = 0

  def get = {
    idCount += 1
    idCount - 1
  }
}
