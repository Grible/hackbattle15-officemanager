package model

import java.time.LocalDateTime

import controllers.routes

/**
 * Created by steven on 22/04/15.
 */

case class Person(name: String,
                  phone: String,
                  avatarSrc: String = routes.Assets.at("images/favicon.png").url,
                  id: String = java.util.UUID.randomUUID().toString,
                  dateAdded: LocalDateTime = LocalDateTime.now()) {
}