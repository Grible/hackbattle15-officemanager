package controllers

import model.Task
import play.api.mvc._
import service.TaskAllocator

/**
 * Created by steven on 23/04/15.
 */
object TaskController extends Controller {
  val listTasksRoute = routes.TaskController.listTasks()

  def addTask = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("description").get.head
    val task: Task = Task(name)
    Tasks.add(task)
    TaskAllocator.allocateTask(task)

    Redirect(listTasksRoute)
  }

  def listTasks = Action { request =>
    Ok(views.html.tasks(Tasks.all))
  }

  def updateTask(id: Int) = Action { request =>
    val name = request.body.asFormUrlEncoded.get.get("name").get.head
    val task = Tasks.get(id.toInt)
    task.name = name
    Redirect(listTasksRoute)
  }

  def deleteTask(id: Int) = Action {
    Tasks.delete(id)
    Redirect(listTasksRoute)
  }

  object Tasks {
    var all: Set[Task] = Set()

    add(Task("doing the dishes"))
    add(Task("vacuum cleaning"))

    def add(task: Task) = all = all + task

    def get(id: Int) = all.find(_.id == id).get

    def delete(id: Int) = all = all - get(id)

    override def toString = all.toString
  }

}
