package commands

import main.Orginize.IOManager
import managers.CityCollection
import models.*
import utils.InputReader
import java.util.Date

class AddCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    private var scriptMode = false
    private var scriptArgs: List<String> = emptyList()
    private var currentScriptIndex = 0

    override fun execute(args: List<String>) {
        if (args.size > 1 && args[0] == "script_mode") {
            scriptMode = true
            scriptArgs = args.drop(1)
            currentScriptIndex = 0
        }

        try {
            val city = if (scriptMode) readCityFromScript() else readCityFromInput()
            val newCity = collection.add(city)
            iomanager.outputLine("Город '${city.name}' успешно добавлен с ID ${newCity.id}")
        } finally {
            scriptMode = false
            scriptArgs = emptyList()
        }
    }

    private fun readCityFromScript(): City {
        if (scriptArgs.size < 12) {
            throw IllegalArgumentException("Недостаточно аргументов в скрипте для создания города")
        }
        return City(
            id = 0,
            name = scriptArgs[currentScriptIndex++],
            coordinates = readCoordinatesFromScript(),
            creationDate = Date(),
            area = scriptArgs[currentScriptIndex++].toFloat(),
            population = scriptArgs[currentScriptIndex++].toLong(),
            metersAboveSeaLevel = scriptArgs[currentScriptIndex++].takeIf { it != "null" }?.toInt(),
            populationDensity = scriptArgs[currentScriptIndex++].toFloat(),
            carCode = scriptArgs[currentScriptIndex++].toLong(),
            government = Government.valueOf(scriptArgs[currentScriptIndex++]),
            governor = if (scriptArgs[currentScriptIndex++] == "y") readGovernorFromScript() else null
        )
    }

    private fun readCoordinatesFromScript(): Coordinates {
        return Coordinates(
            x = scriptArgs[currentScriptIndex++].toDouble(),
            y = scriptArgs[currentScriptIndex++].toLong()
        )
    }

    private fun readGovernorFromScript(): Human {
        return Human(
            name = scriptArgs[currentScriptIndex++],
            height = scriptArgs[currentScriptIndex++].toLong()
        )
    }

    private fun readCityFromInput(): City {
        iomanager.outputLine("\nДобавление нового города:")
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
            governor = if (InputReader.readBoolean("Добавить губернатора?", null, iomanager)) readGovernor() else null
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

    override fun help(): String = "add - добавить новый элемент в коллекцию"
}
