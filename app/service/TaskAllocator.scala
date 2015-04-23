package service

import controllers.CrewController._
import model.{Task, Allocation}

import scala.util.Random

/**
  * Created by steven on 23/04/15.
 */
object TaskAllocator {

  var allocations: Set[Allocation] = Set()
  val selectRandomPerson = () => Random.shuffle(Crew.persons).head

  def allocateTask(task: Task): Allocation = {
    val alloc = Allocation(selectRandomPerson(), task)
    notifyPerson(alloc)
    allocations = allocations + alloc
    alloc
  }

  def notifyPerson(allocation: Allocation) {

    val taskName: String = allocation.task.name
    val username: String = allocation.person.name
    val mobilenumber: String = allocation.person.phone
    val message = s"Hi $username! You have a task: $taskName!"

    println(taskName + " " + username + " " + mobilenumber)
    SMSSender.sendSms(mobilenumber, message)
    println("\nallocation for task:\t" + taskName + "\nto user:\t\t" + username + "\nSms Notification send!\n")
  }
}
