package model

import java.io.{InputStream, ByteArrayInputStream}
import java.nio.charset.StandardCharsets
import scala.beans.BeanProperty

import org.yaml.snakeyaml.Yaml

/**
 * Created by steven on 22/04/15.
 */
class Task() {
  @BeanProperty var name: String = ""
  @BeanProperty var id: Int = IDGen.get
}

object Task {
  def apply(name: String) = {
    val task: Task = new Task()
    task.name = name
    task
  }

  implicit def taskToYamlInputStream(task: Task): InputStream = {
    val yaml: Yaml = new Yaml()
    val output: String = yaml.dump(task)
    println(s"converted to $output")
    new ByteArrayInputStream(output.getBytes(StandardCharsets.UTF_8))
  }

}