package model

import java.io.{InputStream, ByteArrayInputStream}
import java.nio.charset.StandardCharsets

import scala.beans.BeanProperty

import org.yaml.snakeyaml.{DumperOptions, Yaml}

/**
 * Created by steven on 22/04/15.
 */
class Task() {
  @BeanProperty var name: String = ""
  var id: Int = IDGen.get
  val schedule: List[WeekDay.ValueSet] = List(WeekDay.values)

}

object WeekDay extends Enumeration {
  val mon = Value("Monday")
  val tue = Value("Tuesday")
  val wed = Value("Wednesday")
  val thu = Value("Thursday")
  val fri = Value("Friday")
  val sat = Value("Saturday")
  val sun = Value("Sunday")
}

object Task {
  def apply(name: String, schedule: Option[Seq[String]]) = {
    val task: Task = new Task()
    task.name = name
    task
  }

  implicit def taskToYamlInputStream(task: Task): InputStream = {
    val options = new DumperOptions()
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    val yaml: Yaml = new Yaml(options)
    val output: String = yaml.dump(task)
    new ByteArrayInputStream(output.getBytes(StandardCharsets.UTF_8))
  }

}




