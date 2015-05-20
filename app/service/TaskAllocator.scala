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


  override def processAllocatableEvent(event: AllocatableUpdatedEvent): Unit = {
    event match {
      case event: TaskDeletedEvent =>
        deleteAllocationsWithTaskID(event.id)
      case event: TaskUpdatedEvent =>
        updateAllocationsWithTaskID(event.id, event.newTask)
    }
  }

  private def updateAllocationsWithTaskID(id: String, newTask: Task): Unit = {
    val allocationsToUpdate = allocations.filter(_.task.id == id)

    for (alloc <- allocationsToUpdate) {
      allocations -= alloc
      allocations += alloc.copy(task = newTask)
    }
  }

  private def deleteAllocationsWithTaskID(id: String) = allocations = allocations.filterNot(_.task.id == id)

  override def getAllocation(id: String): Allocation = allocations.find(_.task.id == id).get
}

trait TaskAllocator {
  def allocateTask(task: Task): Allocation

  def getAllocation(id: String): Allocation

  def allocations: Set[Allocation]

  def processAllocatableEvent(event: AllocatableUpdatedEvent): Unit
}

sealed trait AllocatableUpdatedEvent {
  def id: String
}

case class TaskUpdatedEvent(id: String, newTask: Task) extends AllocatableUpdatedEvent
case class TaskDeletedEvent(id: String) extends AllocatableUpdatedEvent