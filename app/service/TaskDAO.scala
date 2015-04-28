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

  println("WORKS")
  private val task1: Task = Task("doing the dishes!!!")
  add(task1)
  taskAllocator.allocateTask(task1)
  private val task2: Task = Task("vacuum cleaning")
  taskAllocator.allocateTask(task2)
  add(task2)

  override def add(task: Task) = {
    _all += task
    _all
  }

  override def get(id: Int) = _all.find(_.id == id).get

  override def delete(id: Int) = {
    _all -= get(id)
    _all
  }

  override def toString: String = _all.toString()
}

trait TaskDAO {
  def taskAllocator: TaskAllocator

  def all: Set[Task]

  def add(task: Task): Set[Task]

  def get(id: Int): Task

  def delete(id: Int): Set[Task]

}
