package models

import java.util.*

data class City(
    val id: Long,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: Date = Date(),
    val area: Float,
    val population: Long,
    val metersAboveSeaLevel: Int?,
    val populationDensity: Float,
    val carCode: Long,
    val government: Government,
    val governor: Human?
) : Comparable<City> {

    init {
        require(name.isNotBlank()) { "Название города не может быть пустым" }
        require(area > 0) { "Площадь должна быть положительной" }
        require(population > 0) { "Население должно быть положительным" }
        require(populationDensity > 0) { "Плотность населения должна быть положительной" }
        require(carCode in 1..1000) { "Код автомобилей должен быть от 1 до 1000" }
    }

    override fun compareTo(other: City): Int = this.area.compareTo(other.area)

    override fun toString(): String {
        return """
            |Город: $name (ID: $id)
            |Координаты: (${coordinates.x}, ${coordinates.y})
            |Площадь: $area кв.км
            |Население: $population чел.
            |Высота над уровнем моря: ${metersAboveSeaLevel ?: "не указана"} м
            |Плотность населения: $populationDensity чел./кв.км
            |Код автомобилей: $carCode
            |Форма правления: $government
            |Губернатор: ${governor?.name ?: "не указан"}
            |Дата создания записи: $creationDate
        """.trimMargin()
    }
}
