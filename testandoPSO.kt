import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.*

fun main(){

    fun pow(x: MultiArray<Double, D1>): MultiArray<Double, D1>{
        return x.map{ it * it }
    }

    fun squareFun(x: MultiArray<Double, D1>): Double {
        val res = pow(x).sum()
        return res
    }

//    fun ackley(x: MultiArray<Double, D1>): Double {
//        val a = 20.0
//        val b = 0.2
//        val c = 2.0 * kotlin.math.PI
//        val d = x.size
//        val sum1 =  pow(x).sum()
//        val sum2 = x.map{ c * cos(it) }.sum()
//
//        val term1 =  -a * exp(-b*sqrt(sum1/d.toDouble()))
//        val term2 = -1.0 * exp(sum2/d.toDouble())
//
//        val y = term1 + term2 + a + exp(1.0)
//        return y
//    }
    val start =  System.currentTimeMillis()
    var t = 0
    val nPart = 30
        var iter = 0
    val modeloPSO = PSO({ squareFun(it) }, nPart, 5, 0.75, 1.5, 1.5, -5.12, 5.12)
    while (iter < 100) {
        modeloPSO.atualizaVel()
        modeloPSO.atualizaPart()
        modeloPSO.fitness()
        println("iter $iter - ${modeloPSO.gBestValue}")
        iter += 1
    }
    val end = System.currentTimeMillis()
    println("Time exec: ${end - start}")
}
