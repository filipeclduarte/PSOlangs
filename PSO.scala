import scala.collection.immutable.BitSet.empty
import scala.util.Random
//import scala.collection.parallel.CollectionConverters._ // parallel collections

class PSO(val f: Array[Double] => Double, val nPart: Int, val dim: Int,
          val w: Double, val c1: Double, val c2: Double, val mini: Double, val maxi: Double) {

  val X = Array.fill(nPart, dim){ (maxi - mini) * Random.nextDouble() + mini }
  val V = Array.fill(nPart, dim)(0.0)
  val pBest = X map { i =>
    i map { j =>
      j
    }
  }
  val fit = Array.ofDim[Double](nPart)
  val pBestValue = Array.ofDim[Double](nPart)
  var g0 = 0.0
  var gBest = 0
  var gBestValue = 1.0e+10
  for (i <- 0 until nPart){
    g0 = f(X(i))
    fit(i) = g0
    pBestValue(i) = g0
    if (g0 < gBestValue){
      gBestValue = g0
      gBest = i
    }
  }
//  gBest = fit.indexOf(fit.min)
//  gBestValue = f(X(gBest))

  def atualizaVel() = {
    for (i <- 0 until nPart) {
      val r1 = Random.nextDouble()
      val r2 = Random.nextDouble()
      for (j <- 0 until dim) {
        V(i)(j) = (V(i)(j) * w) + (c1 * r1 * (pBest(i)(j) - X(i)(j))) + (c2 * r2 * (X(gBest)(j) - X(i)(j)))
      }
    }
  }

  def atualizaPart() = {
    //  avalia condições
    for (i <- 0 until nPart) {
      for (j <- 0 until dim) {
        X(i)(j) = X(i)(j) + V(i)(j)
        if (X(i)(j) < mini) {
          X(i)(j) = mini
        }
        else if (X(i)(j) > maxi) {
          X(i)(j) = maxi
        }
      }
    }
  }

  def fitness() = {
//  calculando fitness
    for (i <- 0 until nPart){
      fit(i) = f(X(i))
    }
    // avaliando solucoes
    for (i <- 0 until nPart) {
      if (fit(i) < pBestValue(i)) {
        pBestValue(i) = fit(i)
        for (j <- 0 until dim) {
          pBest(i)(j) = X(i)(j)
        }
      }
      if (fit(i) < gBestValue) {
        gBest = i
        gBestValue = fit(i)
      }
    }
  }
}