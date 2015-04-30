package service

import model.Task
import scaldi.Injectable._
import scaldi.Injector

/**
 * Created by damirv on 24/04/15.
 */
class DummyTaskDAO(implicit inj: Injector) extends TaskDAO {
  override val taskAllocator = inject[TaskAllocator]

  private var _all: Set[Task] = Set()

  override def all: Set[Task] = _all

  private val task1: Task = Task("do the dishes")
  add(task1)
  taskAllocator.allocateTask(task1)
  private val task2: Task = Task("vacuum cleaning")
  taskAllocator.allocateTask(task2)
  add(task2)

  override def add(task: Task) =  _all += task

  override def get(id: String) = _all.find(_.id == id).get

  override def delete(task: Task) = _all -= task

  override def toString: String = _all.toString()

  override def set(task: Task, updatedTask: Task) = _all = _all - task + updatedTask
}

trait TaskDAO {
  def taskAllocator: TaskAllocator

  def all: Set[Task]

  def add(task: Task): Unit

  def get(id: String): Task

  def set(task: Task, updatedTask: Task): Unit

  def delete(task: Task): Unit

}
