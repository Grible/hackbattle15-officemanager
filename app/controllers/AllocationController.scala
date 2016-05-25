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
    val maybeAllocations: Iterable[Option[Allocation]] = allocationDAO.allocations
      .sortWith((left: Allocation, right: Allocation) => left.executionDate.isBefore(right.executionDate))
      .filterNot(_.executionDate.isBefore(LocalDate.now()))
      .groupBy(_.taskID)
      .filter(_._2.nonEmpty)
      .values
      .map(_.headOption)

    val taskWithMetas = maybeAllocations flatMap {
      case Some(a) => {
        val maybeRes = for {
          task <- taskDAO.get(a.taskID)
          person <- personDAO.get(a.personID)
        } yield {
            TaskWithMeta(task, a, person)
          }
        maybeRes.fold(Seq.empty[TaskWithMeta])(Seq(_))
      }
      case None => Seq.empty[TaskWithMeta]
    } toSeq



    //    taskWithMetas.map {
    //      case Some(a) => {
    //        for {
    //          task <- taskDAO.get(a.taskID)
    //          person <- personDAO.get(a.personID)
    //        } yield {
    //          TaskWithMeta(task, a, person)
    //        }
    //      }
    //      case None => None
    //    }
    //
    //    val res = taskWithMetas.map(_.flatMap { a =>
    //      for {
    //        task <- taskDAO.get(a.taskID)
    //        person <- personDAO.get(a.personID)
    //      } yield {
    //        TaskWithMeta(task, a, person)
    //      }
    //    })
    //    val allocationsWithMeta: Seq[TaskWithMeta] = taskWithMetas
    //      .map(_._2)
    //      .map((alloc: Allocation) => {
    //          for {
    //            task <- taskDAO.get(a.taskID)
    //            person <- personDAO.get(a.personID)
    //          } yield {
    //            TaskWithMeta(task, alloc, person)
    //          }
    //        })
    //      .filter(_.isDefined)
    //      .map(_.get)
    //      .toSeq

    Ok(views.html.taskoverview(taskWithMetas))
  }

}

case class TaskWithMeta(task: Task, upcomingAlloc: Allocation, upcomingAllocPerson: Person)
