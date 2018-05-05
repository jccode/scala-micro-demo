package tk.jcchen.hello.micros

import scala.annotation.StaticAnnotation
import scala.reflect.macros.whitebox


// Macro annotations are only available with the macro paradise plugin (in Scala 2.10.x, 2.11.x and 2.12.x alike).
// Their inclusion in official Scala might happen in Scala 2.13, but there is no certainty about it yet.
// Follow the instructions at the “Macro Paradise” page to download and use our compiler plugin.

class Benchmark extends StaticAnnotation {

  def macroTransform(annottees: Any*): Any = macro BenchmarkMacro.impl

}

object BenchmarkMacro {

  def impl(c: whitebox.Context)(annottees: c.Tree*): c.Tree = {
    import c.universe._

    println("compile Benchmark macro")
    annottees.head match {
      case q"$mods def $mname[..$tpes](...$args): $rettpe = { ..$stats }" => {
        q"""
           $mods def $mname[..$tpes](...$args): $rettpe = {
             val start = System.nanoTime()
             val result = {..$stats}
             val end = System.nanoTime()
             println(${mname.toString} + " elapsed time in nano second = " + (end-start).toString())
             result
           }
         """
      }
      case _ => c.abort(c.enclosingPosition, "Incorrect method signature!")
    }
  }
}
