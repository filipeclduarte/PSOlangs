import breeze.linalg._
import scala.util.Random

class PSO(val f: DenseVector[Double] => Double, val nPart: Int, val dim: Int,
          val w: Double, val c1: Double, val c2: Double, val mini: Double, val maxi: Double) {

  var X = (maxi - mini) * rand((nPart, dim)) + mini
  var V = DenseMatrix.zeros[Double](nPart, dim)
  var pBest = X.copy
  var fit = (0 until nPart).map{ i =>
    f(X(i, ::).t)
  }
  var pBestValue = (0 until nPart).map{ i =>
    f(pBest(i, ::).t)}
  var gBest: Int = fit.indexOf(fit.min)
  var gBestValue: Double = f(X(gBest, ::).t)

  def atualizaVel() = {
    for (i <- 0 until V.rows){
      val r1 = (maxi - mini) * Random.nextDouble() + mini
      val r2 = (maxi - mini) * Random.nextDouble() + mini
      V(i, ::).t := (V(i, ::).t * w) + (c1 * r1 * (pBest(i, ::).t - X(i, ::).t)) + (c2 * r2 * (X(gBest, ::).t - X(i, ::).t))

    }
  }

  def atualizaPart() = {
    for (i <- 0 until X.rows){
      X(i, ::).t := X(i, ::).t + V(i, ::).t
    }
    //  avalia condições
    for (i <- 0 until X.rows){
      for (j <- 0 until X.cols){
        if (X(i,j) < mini){
          X(i, j) = mini
        }
        else if (X(i, j) > maxi){
          X(i, j) = maxi
        }
      }
    }
  }

  def fitness() = {
    // calculando fitness
    fit = (0 until nPart).map{ i =>
      f(X(i,::).t) }
    // avaliando solucoes
    for (i <- 0 until X.rows) {
      if (fit(i) < pBestValue(i)){
        pBest(i, ::).t := X(i,::).t
      }
      if (fit(i) < gBestValue){
        gBest = i
        gBestValue = fit(i)
      }
    }
    pBestValue = (0 until nPart).map{ i =>
      f(pBest(i, ::).t) }
  }

}
