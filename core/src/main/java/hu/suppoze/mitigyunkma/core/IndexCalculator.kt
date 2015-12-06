package hu.suppoze.mitigyunkma.core

object IndexCalculator {

    private val suruseg: Double = 0.789
    private val molarisTomeg: Double = 46.07

    data class CalculationParameters(
        val percent: Double,
        val capacity: Double,
        val price: Double,
        val 
    );

    fun calculateIndex(percent: Double, capacity: Double, price: Double): Double {
        var index: Double

        index = capacity * (percent / 100) * 100
        index *= (suruseg / molarisTomeg)
        index = (price / (index * 100) * 100)

        return index;
    }

}