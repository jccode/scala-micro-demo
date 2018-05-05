package tk.jcchen.hello

import micros.Benchmark

object BenchmarkApp extends App {

  @Benchmark
  def testAdd(): Double = {
    val x = 2.0 + 2.0
    Math.pow(x, x)
  }

  @Benchmark
  def testAddWithArgs(x: Double, y: Double) = Math.pow(x, y)


  println(testAdd())
  println(testAddWithArgs(2.0, 3.0))

}
