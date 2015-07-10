package controllers

import dao.TaskDAO
import play.api.mvc._
import scaldi.Injectable._
import scaldi.Injector
import service.{Notifier, TaskAllocator}

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
//    Ok(views.html.tasks(taskAllocator.allocations))
    Ok("started")
  }

}
