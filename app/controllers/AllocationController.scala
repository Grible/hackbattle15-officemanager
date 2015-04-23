package controllers

import controllers.CrewController._
import controllers.TaskController.Tasks
import model.Allocation
import play.api.mvc._

import scala.util.Random

/**
 * Created by steven on 23/04/15.
 */
object AllocationController extends Controller {
  def allocate = Action {
    Allocations.shuffle
    Ok(Allocations.toString)
  }

  object Allocations {
    var all:Set[Allocation] = Set()

    def shuffle = {
      all = Tasks.all.map({
        Allocation(selectRandomPerson(), _)
      })
    }

    val selectRandomPerson = () => Random.shuffle(Crew.persons).head

    override def toString = all.mkString("\n")

  }
}
