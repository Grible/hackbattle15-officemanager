package dao

import java.time.LocalDate

import model.{Allocation, Person, Task}
import scaldi.Injectable._
import scaldi.Injector
/**
 * Created by steven on 11/06/15.
 */
class DummyAllocationDAO(implicit inj: Injector) extends AllocationDAO {
  val taskDAO: TaskDAO = inject[TaskDAO]
  val crewDAO: PersonDAO = inject[PersonDAO]

  val arthur = Person("Arthur", "1234567")
  val steven = Person("Steven", "9874567")
  crewDAO.add(arthur)
  crewDAO.add(steven)

  val dishes = Task("the dishes")
  val vacuum = Task("vacuum cleaning")
  taskDAO.add(dishes)
  taskDAO.add(vacuum)

  val allocation1 = Allocation(arthur.id, dishes.id, LocalDate.now())
  val allocation2 = Allocation(steven.id, vacuum.id, LocalDate.now())

  override def allocations: List[Allocation] = List(allocation1, allocation2)
}

trait AllocationDAO {
  def allocations: List[Allocation]
}