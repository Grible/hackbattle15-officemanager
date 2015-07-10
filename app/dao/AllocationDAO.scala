package dao

import java.time.LocalDate

import model.{Allocation, Person, Task}
import scaldi.Injector

/**
 * Created by steven on 11/06/15.
 */
class DummyAllocationDAO(implicit inj: Injector) extends AllocationDAO {
  val arthur = Person("Arthur", "1234567")
  val steven = Person("Steven", "9874567")
  val dishes = Task("Do the dishes")
  val vacuum = Task("Vacuum clean")

  val allocation1 = Allocation(arthur, dishes, LocalDate.now())
  val allocation2 = Allocation(steven, vacuum, LocalDate.now())

  override def getAllocations: List[Allocation] = List(allocation1, allocation2)
}

trait AllocationDAO {
  def getAllocations: List[Allocation]
}