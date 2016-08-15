package hu.suppoze.mitigyunkma.extension

fun Double.prettyPrint() : String {
    val i = this.toInt()
    return if (this.mod(i) == 0.0) this.toInt().toString() else this.toString()
}
