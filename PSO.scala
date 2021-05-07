import breeze.linalg._
import scala.util.Random
//import scala.collection.parallel.CollectionConverters._ // parallel collections

class PSO( f: DenseVector[Double] => Double, val nPart: Int, val dim: Int,
          val w: Double, val c1: Double, val c2: Double, val mini: Double, val maxi: Double) {

  val X = (maxi - mini) * rand((nPart, dim)) + mini
  val V = DenseMatrix.zeros[Double](nPart, dim)
  val pBest = X.copy
  val fit = DenseVector.zeros[Double](nPart)
  val pBestValue = DenseVector.zeros[Double](nPart)
  var gBest = 0
  var gBestValue = 1.0e+10
  var g0 = 0.0
  for (i <- 0 until nPart){
    g0 = f(X(i, ::).t)
    fit(i) = g0
    pBestValue(i) = g0
    if (g0 < gBestValue){
      gBestValue = g0
      gBest = i
    }
  }

  def atualizaVel() = {
    for (i <- 0 until nPart) {
      val r1 = Random.nextDouble()
      val r2 = Random.nextDouble()
      for (j <- 0 until dim) {
        V(i,j) = (w * V(i, j)) + (c1 * r1 * (pBest(i, j) - X(i, j))) + (c2 * r2 * (X(gBest,j) - X(i, j)))
      }
    }
  }

  def atualizaPart() = {
    for (i <- 0 until nPart){
      for (j <- 0 until dim){
        X(i,j) = X(i,j) + V(i,j)
      }
    }
    //  avalia condições
    for (i <- 0 until nPart) {
      for (j <- 0 until dim) {
        if (X(i,j) < mini) {
          X(i,j) = mini
        }
        else if (X(i,j) > maxi) {
          X(i,j) = maxi
        }
      }
    }
  }

  def fitness() = {
    // calculando fitness
    var fitTemp = 0.0
    for (i <- 0 until nPart){
      fit(i) = f(X(i,::).t)
    }
    // avaliando solucoes
    for (i <- 0 until nPart) {
      if (fit(i) < pBestValue(i)) {
        for (j <- 0 until dim){
          pBest(i, j) = X(i, j)
          pBestValue(i) = fit(i)
        }
        if (fit(i) < gBestValue){
          gBestValue = fit(i)
          gBest = i
        }
      }
    }
  }
}