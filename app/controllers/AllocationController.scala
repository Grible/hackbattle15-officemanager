package controllers

import model.Allocation
import play.api.mvc._
import scaldi.Injectable._
import scaldi.Injector
import service.{TaskDAO, Notifier, TaskAllocator}

/**
 * Created by steven on 24/04/15.
 */
class AllocationController(implicit inj: Injector) extends Controller {
  val taskAllocator = inject[TaskAllocator]
  val notifier = inject[Notifier]
  val listTasksRoute = routes.AllocationController.listAllocations()

  // TODO: remove this. Only needed to fill with sample data (mislead lazy execution)
  val taskDAO = inject[TaskDAO]

  def listAllocations = Action {
    println(taskAllocator.allocations)
    Ok(views.html.tasks(taskAllocator.allocations))
  }

  def sendReminderOfAllocationWithID(id: Int) = Action { request =>
    val alloc: Allocation = taskAllocator.getAllocation(id)
    remindPersonOfAllocation(alloc)
    Redirect(listTasksRoute)
  }

  private def remindPersonOfAllocation(alloc: Allocation): Unit = {
    val taskName: String = alloc.task.name
    val username: String = alloc.person.name
    val message = s"Hi, Naggy again... you were supposed to $taskName but apparently you didn't do it yet, $username. Please fix it asap, it's better for your karma."
    notifier.notify(alloc.person, message)
  }
}
