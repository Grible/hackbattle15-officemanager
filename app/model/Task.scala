package model

/**
 * Created by steven on 22/04/15.
 */
class Task(_name:String) {
  var name: String = _name
  var id = IDGen.get
}

object Task {
  def apply(name:String) = new Task(name)
}
