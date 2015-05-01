package model

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

/**
 * Created by steven on 23/04/15.
 */
case class Allocation(person: Person, taskID: String, date: Date) {
  def formattedDate = Allocation.dateFormat.format(date)

  override def toString = "at " + formattedDate + " " + taskID + " will be performed by " + person.name
}

object Allocation {
  val dateFormat = new SimpleDateFormat("hh:mm")

  def apply(person: Person, task: Task) = new Allocation(person, task.id, Calendar.getInstance().getTime)
}

