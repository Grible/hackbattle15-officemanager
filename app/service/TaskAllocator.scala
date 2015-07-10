package service


import akka.actor.{Actor, Props}
import dao.{AllocationDAO, CrewDAO, TaskDAO}
import model._
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.libs.Akka
import scaldi.Injectable._
import scaldi.Injector

import scala.concurrent.duration._

/**
 * Created by steven on 23/04/15.
 */
class TaskAllocatorImpl(implicit inj: Injector) extends TaskAllocator {
  val logger = Logger("TaskAllocator")
  val notifier = inject[Notifier]

  val crewDAO: CrewDAO = inject[CrewDAO]
  val taskDAO: TaskDAO = inject[TaskDAO]
  val allocationDAO = inject[AllocationDAO]

  val notifyActor = Akka.system.actorOf(Props(new Actor {
    def receive = {
      case _ ⇒ publishTodaysAllocations
    }
  }))

  Akka.system.scheduler.scheduleOnce(10 seconds, notifyActor, "please send the messages")

  override def publishTodaysAllocations: Unit = {
    val allocations: List[Allocation] = allocationDAO.getAllocations
    allocations.foreach(a => {
      val person = crewDAO.get(a.personID)
      val task = taskDAO.get(a.taskID)
      notifier.notify(person, s"Please do ${task.name}")
    })
  }
}

trait TaskAllocator {
  def publishTodaysAllocations: Unit
}