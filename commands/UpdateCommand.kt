package commands

import main.Orginize.IOManager
import managers.CityCollection
import models.City
import models.Coordinates
import models.Government
import models.Human
import utils.InputReader

class UpdateCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        if (args.isEmpty()) throw IllegalArgumentException("Не указан ID города")
        val id = args[0].toLongOrNull() ?: throw IllegalArgumentException("Некорректный ID")

        val oldCity = collection.findById(id)
            ?: throw IllegalArgumentException("Город с ID $id не найден")

        iomanager.outputLine("Обновление города (ID: $id):")
        val updatedCity = readUpdatedCity(oldCity)

        if (collection.update(id, updatedCity)) {
            iomanager.outputLine("Город с ID $id успешно обновлен")
        }
    }

    private fun readUpdatedCity(oldCity: City): City {
        return City(
            id = oldCity.id,
            name = InputReader.readString("Название города:", oldCity.name, iomanager),
            coordinates = readUpdatedCoordinates(oldCity.coordinates),
            creationDate = oldCity.creationDate,
            area = InputReader.readFloat(iomanager, "Площадь (кв. км):", oldCity.area, min = 0.1f),
            population = InputReader.readLong("Население:", oldCity.population, min = 1, max = 10000, iomanager),
            metersAboveSeaLevel = readUpdatedMeters(oldCity.metersAboveSeaLevel),
            populationDensity = InputReader.readFloat(iomanager, "Плотность населения (чел./кв.км):", oldCity.populationDensity, min = 0.1f),
            carCode = InputReader.readLong("Код автомобилей (1-1000):", oldCity.carCode, 1, 1000, iomanager),
            government = InputReader.readEnum("Форма правления:", Government::class.java, oldCity.government, iomanager),
            governor = readUpdatedGovernor(oldCity.governor)
        )
    }

    private fun readUpdatedCoordinates(old: Coordinates): Coordinates {
        iomanager.outputLine("\nКоординаты города:")
        return Coordinates(
            x = InputReader.readDouble(iomanager, "Координата X:", old.x),
            y = InputReader.readLong("Координата Y (должна быть > -922):", old.y, min = -921, max = 1000000, iomanager)
        )
    }

    private fun readUpdatedMeters(old: Int?): Int? {
        return if (InputReader.readBoolean("Изменить высоту над уровнем моря? (текущая: ${old ?: "не указана"})", null, iomanager)) {
            InputReader.readNullableInt("Новая высота над уровнем моря (м):", iomanager)
        } else old
    }

    private fun readUpdatedGovernor(old: Human?): Human? {
        return when {
            old == null && InputReader.readBoolean("Добавить губернатора?", null, iomanager) -> {
                iomanager.outputLine("\nДанные губернатора:")
                Human(
                    name = InputReader.readString("Имя губернатора:", null, iomanager),
                    height = InputReader.readLong("Рост губернатора (см):", null, min = 1, max = 300, iomanager)
                )
            }
            old != null && InputReader.readBoolean("Изменить данные губернатора? (текущий: ${old.name})", null, iomanager) -> {
                iomanager.outputLine("\nНовые данные губернатора:")
                Human(
                    name = InputReader.readString("Имя губернатора:", old.name, iomanager),
                    height = InputReader.readLong("Рост губернатора (см):", old.height, min = 1, max = 300, iomanager)
                )
            }
            else -> old
        }
    }

    override fun help(): String = "update id - обновить значение элемента коллекции по id"
}
