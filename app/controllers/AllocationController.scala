package controllers

import java.net.URLEncoder


import controllers.CrewController._
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
      println ("shuffled!")
      for (allocation <- all) {
        val taskName: String =  URLEncoder.encode(allocation.task.name, "UTF-8")
        val username: String =  URLEncoder.encode(allocation.person.name, "UTF-8")
        val mobilenumber: String = URLEncoder.encode(allocation.person.phone, "UTF-8")



        println( taskName + " " + username + " " + mobilenumber)
        SMSSender.sendSms(mobilenumber, "Je hebt een taak!")

        println("\nallocation for task:\t" + taskName + "\nto user:\t\t" + username + "\nSms Notification send!\n")
      }


    }

    val selectRandomPerson = () => Random.shuffle(Crew.persons).head

    override def toString = all.mkString("\n")

  }
}
