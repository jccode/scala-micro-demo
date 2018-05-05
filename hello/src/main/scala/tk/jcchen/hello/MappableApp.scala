package tk.jcchen.hello

object MappableApp extends App {

  import micros.Mappable

  case class Person(name: String, age: Int, height: Option[Double])

  def mapify[T : Mappable](t: T) = implicitly[Mappable[T]].toMap(t)

  def materialize[T: Mappable](map: Map[String, Any]): T = implicitly[Mappable[T]].fromMap(map)


  val p = Person("Tom", 18, Some(170.0))
  val map = mapify(p)
  val m = materialize[Person](map)
  println(map)
  println(m)

}
