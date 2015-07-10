package model

import java.time.LocalDateTime

/**
 * Created by steven on 22/04/15.
 */
case class Task(name: String,
                weekDaysToExecute: Seq[String] = Seq.empty[String],
                id: String = java.util.UUID.randomUUID().toString,
                dateAdded: LocalDateTime = LocalDateTime.now())