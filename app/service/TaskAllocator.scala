package service


import dao.AllocationDAO
import model._
import play.api.Logger
import scaldi.Injectable._
import scaldi.Injector

/**
 * Created by steven on 23/04/15.
 */
class TaskAllocatorImpl(implicit inj: Injector) extends TaskAllocator {
  val logger = Logger("TaskAllocator")
  val notifier = inject[Notifier]

  val allocationDAO = inject[AllocationDAO]

  import akka.actor.{Actor, Props}
  import play.api.libs.concurrent.Execution.Implicits._
  import play.libs.Akka

  import scala.concurrent.duration._

  val notifyActor = Akka.system.actorOf(Props(new Actor {
    def receive = {
      case _ ⇒ publishTodaysAllocations
    }
  }))


  Akka.system.scheduler.scheduleOnce(10 seconds, notifyActor, "please send the messages")


  override def publishTodaysAllocations: Unit = {
    val allocations: List[Allocation] = allocationDAO.getAllocations
    allocations.foreach(a => {
      notifier.notify(a.person, s"Please do ${a.task.name}")
    })
  }
}

trait TaskAllocator {
  def publishTodaysAllocations: Unit
}