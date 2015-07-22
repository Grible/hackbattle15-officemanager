import controllers.AllocationController
import dao._
import play.api.GlobalSettings
import scaldi.Module
import scaldi.play.ScaldiSupport
import scaldi.play.condition._
import service._

object Global extends GlobalSettings with ScaldiSupport {
  def applicationModule = new NotifyModule :: new TaskModule :: new CrewModule :: new AllocationModule
}

class NotifyModule extends Module {
  bind[Notifier] when (inDevMode or inTestMode) to new ConsoleNotifier
}

class AllocationModule extends Module {
  binding to new AllocationController
  bind[AllocationDAO] to new DummyAllocationDAO
}

class TaskModule extends Module {
  //  binding to new TaskController
  bind[TaskAllocator] to new TaskAllocatorImpl
  //  bind[TaskDAO] when inProdMode to new DummyTaskDAO // TODO: replace with prod implementation
  bind[TaskDAO] when (inDevMode or inTestMode) to new InMemoryTaskDAO
}

class CrewModule extends Module {
  //  binding to new CrewController
  //  bind[CrewDAO] when inProdMode to new DummyCrewDAO // TODO: replace with prod implementation
  bind[PersonDAO] when (inDevMode or inTestMode) to new InMemoryPersonDAO
}

