package models

data class Coordinates(
    val x: Double,
    val y: Long
) {
    init {
        require(y > -922) { "Координата Y должна быть больше -922" }
    }
}
