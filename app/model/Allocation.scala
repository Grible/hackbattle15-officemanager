package model

import java.text.SimpleDateFormat
import java.util.{Date, Calendar}

/**
 * Created by steven on 23/04/15.
 */
case class Allocation(nagger: Nagger, task:Task, date:Date) {
  def formattedDate = Allocation.dateFormat.format(date)
  override def toString = "at " + formattedDate + " " + task.name + " will be performed by " + nagger.name
}

object Allocation {
  val dateFormat = new SimpleDateFormat("hh:mm")
  def apply(nagger: Nagger, task:Task) = new Allocation(nagger, task, Calendar.getInstance().getTime)
}

