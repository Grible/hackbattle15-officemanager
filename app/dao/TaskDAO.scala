package dao

import model.Task
import scaldi.Injectable._
import scaldi.Injector
import scala.collection.mutable.Seq

/**
 * Created by damirv on 24/04/15.
 */
class InMemoryTaskDAO(implicit inj: Injector) extends TaskDAO {
  private var _all: Seq[Task] = Seq()

  override def all: Seq[Task] = _all

  override def add(task: Task) = _all = _all.+:(task)

  override def get(id: String) = _all.find(_.id == id)

  override def delete(id: String) = {
    get(id) match {
      case Some(t: Task) => {
        _all = _all.filterNot(_ == t)
        Right(t)
      }
      case None => Left("Could not delete task, does not exist")
    }
  }

  override def toString: String = _all.toString()

  override def update(taskID: String, updatedTask: Task) = {
    _all = _all.map{
      case Task(_,_,taskID,_) => updatedTask
      case t: Task => t
    }
  }
}

trait TaskDAO {
  def all: Seq[Task]

  def add(task: Task): Unit

  def get(id: String): Option[Task]

  def update(taskID: String, updatedTask: Task): Unit

  def delete(id: String): Either[String, Task]
}
