package service


import model.{Allocation, Task}
import play.api.Logger
import scaldi.Injectable._
import scaldi.Injector

import scala.util.Random

/**
 * Created by steven on 23/04/15.
 */
class TaskAllocatorImpl(implicit inj: Injector) extends TaskAllocator {
  val logger = Logger("TaskAllocator")
  val notifier = inject[Notifier]
  val crewDAO = inject[CrewDAO]

  var allocations: Set[Allocation] = Set()
  val selectRandomPerson = () => Random.shuffle(crewDAO.persons).head

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
    val person = allocation.person

    logger.info(taskName + " " + username + " " + mobilenumber)
    notifier.notify(person, message)
    logger.info("\nallocation for task:\t" + taskName + "\nto user:\t\t" + username + "\nSms Notification send!\n")
  }
}

trait TaskAllocator {
  def notifyPerson(allocation: Allocation): Unit

  def allocateTask(task: Task): Allocation

  def allocations: Set[Allocation]
}