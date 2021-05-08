
import scala.language.postfixOps

object testePSO extends App {

  def squareFun(x: Array[Double]): Double = {
    x map { math.pow(_, 2.0) } sum
  }

  val start = System.currentTimeMillis()
  val nPart = 30
  var iter = 0
  val modeloPSO = new PSO(squareFun, nPart, 5, 0.75, 1.5, 1.5, -5.12, 5.12)
  while (iter < 100) {
    modeloPSO.atualizaVel()
    modeloPSO.atualizaPart()
    modeloPSO.fitness()
    println(s"iter: $iter - gBestValue: ${modeloPSO.gBestValue}")
    iter += 1
    }

  val end = System.currentTimeMillis()
  println(s"Time exec: ${end - start}")

}
