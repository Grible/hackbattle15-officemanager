package controllers

import java.time.LocalDate

import dao.{AllocationDAO, PersonDAO, TaskDAO}
import model.{Allocation, Person, Task}
import play.api.mvc._
import play.libs.Json
import scaldi.Injectable._
import scaldi.Injector

/**
 * Created by steven on 24/04/15.
 */
class AllocationController(implicit inj: Injector) extends Controller {
  val allocationDAO = inject[AllocationDAO]
  val taskDAO = inject[TaskDAO]
  val personDAO = inject[PersonDAO]
  //  val listTasksRoute = routes.AllocationController.listAllocations()

  def listAllocations = Action {

    val allocationsWithMeta: Seq[TaskWithMeta] = allocationDAO.allocations
      .sortWith((left: Allocation, right: Allocation) => left.executionDate.isBefore(right.executionDate))
      .filterNot(_.executionDate.isBefore(LocalDate.now()))
      .groupBy(_.taskID)
      .mapValues(_.head)
      .map(_._2)
      .map((alloc: Allocation) => {
          for {
            task <- taskDAO.get(alloc.taskID)
            person <- personDAO.get(alloc.personID)
          } yield {
            TaskWithMeta(task, alloc, person)
          }
        })
      .filter(_.isDefined)
      .map(_.get)
      .toSeq

    println(allocationsWithMeta)

    Ok(views.html.taskoverview(allocationsWithMeta))
  }

}

case class TaskWithMeta(task: Task, upcomingAlloc: Allocation, upcomingAllocPerson: Person)
