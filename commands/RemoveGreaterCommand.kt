package commands

import main.Orginize.IOManager
import managers.CityCollection
import models.City
import models.Coordinates
import models.Government
import models.Human
import utils.InputReader
import java.util.Date

class RemoveGreaterCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        val city = readCityFromInput()
        val count = collection.removeAll { it > city }
        iomanager.outputLine("Удалено $count элементов, превышающих заданный")
    }

    private fun readCityFromInput(): City {
        iomanager.outputLine("\nВведите город для сравнения:")
        return City(
            id = 0,
            name = InputReader.readString("Название города:", null, iomanager),
            coordinates = readCoordinates(),
            creationDate = Date(),
            area = InputReader.readFloat(iomanager, "Площадь (кв. км):", min = 0.1f),
            population = InputReader.readLong("Население:", null, min = 1, max = 10000000, iomanager),
            metersAboveSeaLevel = InputReader.readNullableInt("Высота над уровнем моря (м):", iomanager),
            populationDensity = InputReader.readFloat(iomanager, "Плотность населения (чел./кв.км):", min = 0.1f),
            carCode = InputReader.readLong("Код автомобилей (1-1000):", null, min = 1, max = 1000, iomanager),
            government = InputReader.readEnum("Форма правления:", Government::class.java, iomanager),
            governor = if (InputReader.readBoolean("Добавить губернатора?", null, iomanager)) {
                readGovernor()
            } else null
        )
    }

    private fun readCoordinates(): Coordinates {
        iomanager.outputLine("\nКоординаты города:")
        return Coordinates(
            x = InputReader.readDouble(iomanager, "Координата X:"),
            y = InputReader.readLong("Координата Y (должна быть > -922):", null, min = -921, max = 10000, iomanager)
        )
    }

    private fun readGovernor(): Human {
        iomanager.outputLine("\nДанные губернатора:")
        return Human(
            name = InputReader.readString("Имя губернатора:", null, iomanager),
            height = InputReader.readLong("Рост губернатора (см):", null, min = 1, max = 300, iomanager)
        )
    }

    override fun help(): String = "remove_greater - удалить из коллекции все элементы, превышающие заданный"
}
