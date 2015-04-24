package controllers

import model.Task
import play.api.mvc._
import scaldi.Injectable._
import scaldi.Injector
import service.{TaskAllocator, TaskDAO}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by steven on 23/04/15.
 */
class TaskController(implicit inj: Injector) extends Controller {
  val taskAllocator = inject[TaskAllocator]
  val taskDAO = inject[TaskDAO]
  val listTasksRoute = routes.TaskController.listAllocations()
  val allTasksDummyNeededForInit = taskDAO.all

  def addTask = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("description").get.head
    val schedule = request.body.asFormUrlEncoded.get.get("schedule")

    println("\nSCHEDULE " + schedule + "\n")
    val task: Task = Task(name, schedule)
    taskDAO.add(task)
    println("\nSCHEDULE " + task.schedule + "\n")
    taskAllocator.allocateTask(task)

    Redirect(listTasksRoute)
  }

  def listAllocations = Action {
    Ok(views.html.tasks(taskAllocator.allocations))
  }

  def updateTask(id: Int) = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val task = taskDAO.get(id.toInt)
    task.name = name
    Redirect(listTasksRoute)
  }

  def deleteTask(id: Int) = Action {
    taskDAO.delete(id)
    Redirect(listTasksRoute)
  }

}
