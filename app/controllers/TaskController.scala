package controllers

import dao.TaskDAO
import model.Task
import play.api.mvc._
import scaldi.Injectable._
import scaldi.Injector

/**
 * Created by steven on 23/04/15.
 */
class TaskController(implicit inj: Injector) extends Controller {
  //  val taskAllocator = inject[TaskAllocator]
  //    val taskDAO = inject[TaskDAO]
  val taskDAO = inject[TaskDAO]
  val listTasksRoute = routes.AllocationController.listAllocations()
  //  val allTasksDummyNeededForInit = taskDAO.all

  //  def addAndAllocateTask = Action { request =>
  //    val name = request.body.asFormUrlEncoded.get.get("description").get.head
  //    val schedule = request.body.asFormUrlEncoded.get.get("schedule")
  //
  //    println("\nSCHEDULE " + schedule + "\n")
  //    val task: Task = Task(name)
  //    taskDAO.add(task)
  //    taskAllocator.allocateTask(task)
  //    Redirect(listTasksRoute)
  //  }
  //
//  def updateTask(id: String) = Action { request =>
//    val name = request.body.asFormUrlEncoded.get.get("name").get.head
//
//    val updatedTask: Task = for {
//      fields <- request.body.asFormUrlEncoded
//      name <- fields.get("name").head
//      task <- taskDAO.get(id)
//    } yield {
//        task.copy(name = name)
//      }
//
//    taskDAO.set(updatedTask.id, updatedTask)
//    val event = TaskUpdatedEvent(id, updatedTask)
//    Redirect(listTasksRoute)
//  }

  //
  //  def deleteTask(id: String) = Action {
  //    val task = taskDAO.get(id)
  //    taskDAO.delete(task)
  //    val event = TaskDeletedEvent(id)
  //    taskAllocator.processAllocatableEvent(event)
  // TODO: remove allocations
  //    Redirect(listTasksRoute)
  //  }
}
