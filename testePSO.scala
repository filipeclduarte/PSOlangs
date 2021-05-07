
import breeze.linalg._
import breeze.numerics.pow

import scala.language.postfixOps

object testePSO extends App {

  def squareFun(x: DenseVector[Double]): Double = {
    sum(x *:* x)
  }

  val start = System.currentTimeMillis()
  val nPart = 30
  var t = 0
  while(t < 1) {
    var iter = 0
    val modeloPSO = new PSO(squareFun, nPart, 5, 0.75, 1.5, 1.5, -5.12, 5.12)
    while (iter < 100) {
      modeloPSO.atualizaVel()
      modeloPSO.atualizaPart()
      modeloPSO.fitness()
//      println(s"iter: $iter")
      println(s"iter: $iter - gBestValue: ${modeloPSO.gBestValue}")
      iter += 1
    }
    t += 1
  }
  val end = System.currentTimeMillis()
  println(s"Time exec: ${end - start}")

}
