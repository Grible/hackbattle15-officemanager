package model

import java.io.{InputStream, ByteArrayInputStream}
import java.nio.charset.StandardCharsets

import scala.beans.BeanProperty

import org.yaml.snakeyaml.{DumperOptions, Yaml}

/**
 * Created by steven on 22/04/15.
 */
case class Task(name: String,
                weekDaysToExecute:Seq[String] = Seq.empty[String],
                id: String = java.util.UUID.randomUUID().toString)

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
  implicit def taskToYamlInputStream(task: Task): InputStream = {
    val options = new DumperOptions()
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    val yaml: Yaml = new Yaml(options)
    val output: String = yaml.dump(task)
    new ByteArrayInputStream(output.getBytes(StandardCharsets.UTF_8))
  }
}
