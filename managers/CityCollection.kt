package managers

import models.City
import utils.CsvParser
import java.io.IOException
import java.util.*

class CityCollection {
    private val cities: LinkedHashSet<City> = LinkedHashSet()
    private var nextId: Long = 1
    private val initializationDate: Date = Date()

    fun add(city: City): City {
        val newCity = city.copy(id = nextId++)
        if (cities.any { it.id == newCity.id }) {
            throw IllegalArgumentException("Город с ID ${newCity.id} уже существует")
        }
        cities.add(newCity)
        return newCity
    }

    fun update(id: Long, newCity: City): Boolean {
        return if (cities.removeIf { it.id == id }) {
            cities.add(newCity.copy(id = id))
            true
        } else {
            false
        }
    }

    fun removeById(id: Long): Boolean = cities.removeIf { it.id == id }

    fun removeAll(predicate: (City) -> Boolean): Int {
        val sizeBefore = cities.size
        cities.removeIf(predicate)
        return sizeBefore - cities.size
    }

    fun clear() {
        cities.clear()
        nextId = 1
    }

    fun findById(id: Long): City? = cities.firstOrNull { it.id == id }

    fun getInfo(): String {
        return """
            |Тип коллекции: ${cities::class.simpleName}
            |Дата инициализации: $initializationDate
            |Количество элементов: ${cities.size}
            |Следующий доступный ID: $nextId
        """.trimMargin()
    }

    fun getAll(): List<City> = cities.toList()

    fun size(): Int = cities.size

    fun isEmpty(): Boolean = cities.isEmpty()

    @Throws(IOException::class)
    fun saveToFile(filename: String) {
        CsvParser.saveToFile(filename, cities)
    }

    @Throws(IOException::class)
    fun loadFromFile(filename: String) {
        val loadedCities = CsvParser.loadFromFile(filename)
        cities.clear()
        cities.addAll(loadedCities)
        nextId = (cities.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun minByCreationDate(): City? = cities.minByOrNull { it.creationDate }

    fun sumOfMetersAboveSeaLevel(): Long {
        return cities.filter { it.metersAboveSeaLevel != null }
            .sumOf { it.metersAboveSeaLevel!!.toLong() }
    }

    fun countLessThanMeters(meters: Int): Int {
        return cities.count {
            it.metersAboveSeaLevel != null &&
                    it.metersAboveSeaLevel!! < meters
        }
    }
}
