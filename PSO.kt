import org.jetbrains.kotlinx.multik.api.*
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import org.jetbrains.kotlinx.multik.default.DefaultMath.argMin
import kotlin.random.Random

class PSO(val f: (MultiArray<Double, D1>) -> Double, val nPart: Int, val dim: Int, val w: Double, val c1: Double,
          val c2: Double, val mini: Double, val maxi: Double) {
    val X = mk.empty<Double, D2>(nPart, dim).map{ (maxi - mini) * Random.nextDouble() + mini }
    val V = mk.empty<Double, D2>(nPart, dim)
    val pBest = X.clone()
    var fit = (0 until nPart).map{f(X[it])}
    var pBestValue = (0 until nPart).map{f(pBest[it])}
    var gBest: Int = fit.indexOf(fit.minOrNull())
    var gBestValue = f(X[gBest])

    fun atualizaVel() {
        for (i in 0 until V.shape[0]){
            val r1 = (maxi - mini) * Random.nextDouble() + mini
            val r2 = (maxi - mini) * Random.nextDouble() + mini
            V[i] = (V[i] * w) + (c1 * r1 * (pBest[i] - X[i])) + (c2 * r2 * (X[gBest] - X[i]))
        }
    }

    fun atualizaPart() {
        for (i in 0 until X.shape[0]){
            X[i] = X[i] + V[i]
        }
//         avalia condições
        for (i in 0 until X.shape[0]){
            for (j in 0 until X.shape[1]){
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
        for (i in 0 until X.shape[0]) {
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