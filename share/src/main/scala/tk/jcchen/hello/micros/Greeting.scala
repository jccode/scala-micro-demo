package tk.jcchen.hello.micros

import java.util.Date

import scala.reflect.macros.blackbox


object Greeting {

  def greeting(person: String): Unit = macro greetingImpl

  def greetingImpl(c: blackbox.Context)(person: c.Expr[String]): c.Expr[Unit]= {
    import c.universe._
    println("compiling greeting")
    val now = reify(new Date().toString())
    reify {
      println("Hello " + person.splice + ", the time is: " + new Date().toString)
    }
  }


  def tell(person: String): Unit = macro tellImpl

  def tellImpl(c: blackbox.Context)(person: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._
    println("compiling tell")
    val now = new Date().toString
    c.Expr[Unit] {
      q"""
       println("Hello "+$person+", it is: "+$now)
        """
    }
  }

  /*
  def tell(person: String): Unit = macro tellImpl

  def tellImpl(c: blackbox.Context)(person: c.Tree): c.Tree = {
    import c.universe._
    println("compiling tell")
    val now = new Date().toString
    q"""
       println("Hello "+$person+", it is: "+$now)
     """
  }
  */

  /*
  def tell(person: String): Unit = macro MacroImpl.tell

  class MacroImpl(val c: blackbox.Context) {
    import c.universe._

    def tell(person: c.Tree): c.Tree = {
      println("compiling tell")
      val now = new Date().toString
      q"""
         println("Hello "+$person+", it is: "+$now)
       """
    }
  }
  */

}
