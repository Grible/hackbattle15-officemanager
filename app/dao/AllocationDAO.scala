package dao

import java.time.LocalDate

import model.{Allocation, Person}
import scaldi.Injector

/**
 * Created by steven on 11/06/15.
 */
class DummyAllocationDAO(implicit inj: Injector) extends AllocationDAO {
  val arthur = Person("Arthur", "1234567")
  val steven = Person("Steven", "9874567")

  val allocation1 = Allocation(arthur, "stofzuigen", LocalDate.of(2015, 5, 11))
  val allocation2 = Allocation(steven, "vaatwasser uitruimen", LocalDate.of(2015, 5, 12))

  override def getAllocations: Set[Allocation] =   Set(allocation1, allocation2)
}

trait AllocationDAO {
  def getAllocations: Set[Allocation]
}