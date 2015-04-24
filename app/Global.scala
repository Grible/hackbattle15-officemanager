import controllers.{CrewController, Dropbox, TaskController}
import play.api.GlobalSettings
import scaldi.Module
import scaldi.play.ScaldiSupport
import scaldi.play.condition._
import service._

object Global extends GlobalSettings with ScaldiSupport {
  def applicationModule = new WebModule :: new SmsModule :: new TaskModule :: new CrewModule
}

class SmsModule extends Module {
  bind[Notifier] when inProdMode to new NexmoNotifier
  bind[Notifier] when (inDevMode or inTestMode) to new ConsoleNotifier
}

class TaskModule extends Module {
  binding to new TaskController
  bind[TaskAllocator] to new TaskAllocatorImpl
  bind[TaskDAO] when inProdMode to new DummyTaskDAO // TODO: replace with prod implementation
  bind[TaskDAO] when (inDevMode or inTestMode) to new DummyTaskDAO
}

class CrewModule extends Module {
  binding to new CrewController
  bind[CrewDAO] when inProdMode to new DummyCrewDAO // TODO: replace with prod implementation
  bind[CrewDAO] when (inDevMode or inTestMode) to new DummyCrewDAO
}

class WebModule extends Module {
  binding to new Dropbox
}