import scala.util.Random
//import scala.collection.parallel.CollectionConverters._ // parallel collections

class PSO(val f: Array[Double] => Double, val nPart: Int, val dim: Int,
          val w: Double, val c1: Double, val c2: Double, val mini: Double, val maxi: Double) {

  var X = Array.fill(nPart, dim){ (maxi - mini) * Random.nextDouble() + mini }
  var V = Array.fill(nPart, dim)(0.0)
  var pBest = X map { i =>
   i.map{ j => j }
}
  var fit = (0 until nPart).map { i =>
    f(X(i))
  }
  var pBestValue = (0 until nPart) map { i =>
  f(pBest(i))
}
  var gBest: Int = fit.indexOf(fit.min)
  var gBestValue = f(X(gBest))

  def atualizaVel() = {
    for (i <- 0 until nPart) {
      val r1 = (maxi - mini) * Random.nextDouble() + mini
      val r2 = (maxi - mini) * Random.nextDouble() + mini
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
    // calculando fitness
    fit = (0 until nPart) map { i =>
      f(X(i))
    }
    // avaliando solucoes
    for (i <- 0 until nPart) {
      if (fit(i) < pBestValue(i)) {
        for (j <- 0 until dim) {
          pBest(i)(j) = X(i)(j)
        }
      }
      if (fit(i) < gBestValue) {
        gBest = i
        gBestValue = fit(i)
      }
    }
    pBestValue = (0 until nPart) map { i =>
      f(pBest(i))
    }
  }
}