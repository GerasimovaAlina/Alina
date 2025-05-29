package utils
/*умеет сохранять коллекцию городов в CSV-файл и загружать её обратно*/
import models.City
import models.Coordinates
import models.Government
import models.Human
import java.io.*
import java.util.*

object CsvParser {

    @Throws(IOException::class)
    fun saveToFile(filename: String, cities: Collection<City>) {
        BufferedWriter(FileWriter(filename)).use { writer ->
            writer.write("id,name,coordinate_x,coordinate_y,creation_date,area,population,meters_above_sea_level,population_density,car_code,government,governor_name,governor_height\n")

            cities.forEach { city ->
                writer.write(
                    "${city.id}," +
                            "\"${escapeCsv(city.name)}\"," +
                            "${city.coordinates.x}," +
                            "${city.coordinates.y}," +
                            "${city.creationDate.time}," +
                            "${city.area}," +
                            "${city.population}," +
                            "${city.metersAboveSeaLevel ?: ""}," +
                            "${city.populationDensity}," +
                            "${city.carCode}," +
                            "${city.government}," +
                            "${city.governor?.name?.let { escapeCsv(it) } ?: ""}," +
                            "${city.governor?.height ?: ""}\n"
                )
            }
        }
    }

    @Throws(IOException::class)
    fun loadFromFile(filename: String): List<City> {
        return BufferedReader(FileReader(filename)).use { reader ->
            reader.lineSequence()
                .drop(1)
                .mapNotNull { line -> parseCityLine(line) }
                .toList()
        }
    }

    private fun parseCityLine(line: String): City? {
        return try {
            val parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                .map { it.trim('"').replace("\"\"", "\"") }

            City(
                id = parts[0].toLong(),
                name = parts[1],
                coordinates = Coordinates(parts[2].toDouble(), parts[3].toLong()),
                creationDate = Date(parts[4].toLong()),
                area = parts[5].toFloat(),
                population = parts[6].toLong(),
                metersAboveSeaLevel = parts[7].takeIf { it.isNotEmpty() }?.toInt(),
                populationDensity = parts[8].toFloat(),
                carCode = parts[9].toLong(),
                government = Government.valueOf(parts[10]),
                governor = if (parts[11].isNotEmpty()) {
                    Human(
                        name = parts[11],
                        height = parts[12].toLong()
                    )
                } else null
            )
        } catch (e: Exception) {
            System.err.println("Ошибка при разборе строки: $line\n${e.message}")
            null
        }
    }

    private fun escapeCsv(value: String): String = value.replace("\"", "\"\"")
}
