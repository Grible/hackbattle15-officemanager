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

  import play.libs.Akka

  import akka.actor.Actor
  import akka.actor.Props
  import scala.concurrent.duration._

  val notifyActor = Akka.system.actorOf(Props(new Actor {
    def receive = {
      case _ ⇒ publishTodaysAllocations
    }
  }))


  //Schedules to send the "foo"-message to the testActor after 50ms
  Akka.system.scheduler.scheduleOnce(10 seconds, notifyActor, "please send the messages")





  override def publishTodaysAllocations: Unit = {
    val allocations: Set[Allocation] = allocationDAO.getAllocations
    allocations.foreach(a => {
      notifier.notify(a.person, s"Please do ${a.taskName}")
    })
  }
}

trait TaskAllocator {
  def publishTodaysAllocations: Unit
}