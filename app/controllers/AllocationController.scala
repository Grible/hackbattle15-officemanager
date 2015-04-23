package controllers

import java.net.URLEncoder

import controllers.NaggerController._
import controllers.TaskController.Tasks
import model.Allocation
import play.api.mvc._
import service.SMSSender

import scala.util.Random

/**
 * Created by steven on 23/04/15.
 */
object AllocationController extends Controller {


  def allocate = Action {

    Allocations.allocateTasksToPersons
    Ok(Allocations.toString)
  }

  object Allocations {

    var all:Set[Allocation] = Set()

    def shuffle = {
      all = Tasks.all.map({
        Allocation(selectRandomPerson(), _)
      })
    }

    def allocateTasksToPersons = {

      shuffle
      for (allocation <- all) {
        val taskName: String =  URLEncoder.encode(allocation.task.name, "UTF-8")
        val username: String =  URLEncoder.encode(allocation.nagger.name, "UTF-8")
        val mobilenumber: String = URLEncoder.encode(allocation.nagger.phone, "UTF-8")

        SMSSender.sendSms(mobilenumber, "Je hebt een taak!")

        println("\nallocation for task:\t" + taskName + "\nto user:\t\t" + username + "\nSms Notification send!\n")
      }


    }

    val selectRandomPerson = () => Random.shuffle(Naggers.all).head

    override def toString = all.mkString("\n")

  }
}
