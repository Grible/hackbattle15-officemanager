package service


import model._
import play.api.Logger
import scaldi.Injectable._
import scaldi.Injector

import scala.util.Random

/**
 * Created by steven on 23/04/15.
 */
class TaskAllocatorImpl(implicit inj: Injector) extends TaskAllocator {
  val logger = Logger("TaskAllocator")
  val crewDAO = inject[CrewDAO]
  val notifier = inject[Notifier]

  var allocations: Set[Allocation] = Set()
  val selectRandomPerson = () => Random.shuffle(crewDAO.persons).head

  def allocateTask(task: Task): Allocation = {
    val alloc = Allocation(selectRandomPerson(), task)
    notifyPersonOfAllocation(alloc)
    allocations = allocations + alloc
    alloc
  }

  private def notifyPersonOfAllocation(alloc: Allocation): Unit = {
    val taskName: String = alloc.task.name
    val username: String = alloc.person.name
    val message = s"Hi $username! This is Naggy. You have a task: $taskName!"
    notifier.notify(alloc.person, message)
  }

  def getAllocation(id: Int): Allocation = {allocations.find(_.task.id == id).get}
}

trait TaskAllocator {
  def allocateTask(task: Task): Allocation

  def getAllocation(id: Int): Allocation

  def allocations: Set[Allocation]
}