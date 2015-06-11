import dao._
import play.api.GlobalSettings
import scaldi.Module
import scaldi.play.ScaldiSupport
import scaldi.play.condition._
import service._

object Global extends GlobalSettings with ScaldiSupport {
  def applicationModule = new NotifyModule :: new AllocationModule :: new TaskModule :: new CrewModule
}

class NotifyModule extends Module {
  bind[Notifier] when (inDevMode or inTestMode) to new ConsoleNotifier
}

class AllocationModule extends Module {
  //  binding to new AllocationController
  bind[AllocationDAO] to new DummyAllocationDAO
}

class TaskModule extends Module {
  //  binding to new TaskController
  bind[TaskAllocator] to new TaskAllocatorImpl
  //  bind[TaskDAO] when inProdMode to new DummyTaskDAO // TODO: replace with prod implementation
  //  bind[TaskDAO] when (inDevMode or inTestMode) to new DummyTaskDAO
}

class CrewModule extends Module {
  //  binding to new CrewController
  //  bind[CrewDAO] when inProdMode to new DummyCrewDAO // TODO: replace with prod implementation
  //  bind[CrewDAO] when (inDevMode or inTestMode) to new DummyCrewDAO
}

