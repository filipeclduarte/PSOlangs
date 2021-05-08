import org.jetbrains.kotlinx.multik.api.*
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import kotlin.random.Random

class PSO(val f: (MultiArray<Double, D1>) -> Double, val nPart: Int, val dim: Int, val w: Double, val c1: Double,
          val c2: Double, val mini: Double, val maxi: Double) {
    val X = mk.empty<Double, D2>(nPart, dim).map{ (maxi - mini) * Random.nextDouble() + mini }
    val V = mk.empty<Double, D2>(nPart, dim)
    var pBest = X.clone()
    var fit = (0 until nPart).map{f(X[it])}
    var pBestValue = (0 until nPart).map{f(pBest[it])}
    var gBest: Int = fit.indexOf(fit.minOrNull())
    var gBestValue = f(X[gBest])

    fun atualizaVel() {
        for (i in 0 until nPart){
            val r1 = Random.nextDouble()
            val r2 = Random.nextDouble()
            V[i] = (V[i] * w) + (c1 * r1 * (pBest[i] - X[i])) + (c2 * r2 * (X[gBest] - X[i]))
        }
    }

    fun atualizaPart() {
//         avalia condições
        for (i in 0 until nPart){
            X[i] = X[i] + V[i]
            for (j in 0 until dim){
                if (X[i,j] < mini){
                    X[i, j] = mini
                }
                else if (X[i, j] > maxi){
                    X[i, j] = maxi
                }
            }
        }
    }

//    fun fitness(f: (MultiArray<Double, D1>) -> Double) {
    fun fitness(){
        // calculando fitness
        fit = (0 until nPart).map{ f(X[it]) }
        // avaliando solucoes
        for (i in 0 until nPart) {
            if (fit[i] < pBestValue[i]){
                pBest[i] = X[i]
            }
            if (fit[i] < gBestValue){
                gBest = i
                gBestValue = fit[i]
            }
        }
        pBestValue = (0 until nPart).map{f(pBest[it])}
    }
}