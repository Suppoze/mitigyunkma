package hu.suppoze.mitigyunkma.core

object IndexCalculator {

    private const val density: Double = 0.80207
    private const val molecularWeight: Double = 46.06844

    private var percent: Double = .0
    private var capacity: Double = .0
    private var price: Double = .0

    fun calculateIndex(bundle: ParameterBundle): Int {
        convert(bundle)
        var index = price / (capacity * percent * (density / molecularWeight) * 100) * 100
        return index.toInt();
    }

    // TODO: presenter layer
    fun convert(bundle: ParameterBundle) {
        percent = bundle.percent.toDouble()
        capacity = bundle.capacity.toDouble()
        price = bundle.price.toDouble()
    }

    public data class ParameterBundle(
            val percent: String,
            val capacity: String,
            val price: String
    )
}