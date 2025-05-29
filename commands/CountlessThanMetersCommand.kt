package commands

import main.Orginize.IOManager
import managers.CityCollection
import utils.InputReader

class CountLessThanMetersCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        try {
            if (collection.getAll().isEmpty()) {
                iomanager.outputLine("Коллекция городов пуста.")
                return
            }

            val threshold = if (args.isNotEmpty()) {
                args[0].toIntOrNull() ?: throw IllegalArgumentException("Некорректное значение высоты")
            } else {
                InputReader.readInt(iomanager, "Введите пороговое значение высоты (в метрах):")
            }

            val count = collection.getAll()
                .count { city ->
                    city.metersAboveSeaLevel != null &&
                            city.metersAboveSeaLevel!! < threshold
                }

            iomanager.outputLine("Количество городов с высотой ниже $threshold метров: $count")

        } catch (e: Exception) {
            iomanager.outputLine("Ошибка: ${e.message}")
            iomanager.outputLine("Правильный формат: count_less_than_meters_above_sea_level [metersAboveSeaLevel]")
        }
    }

    override fun help(): String {
        return """
            |count_less_than_meters_above_sea_level metersAboveSeaLevel - 
            |выводит количество элементов, значение поля metersAboveSeaLevel 
            |которых меньше заданного.
            |Если значение не указано, запрашивает его интерактивно.
        """.trimMargin()
    }
}
