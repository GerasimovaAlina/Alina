package commands

import main.Orginize.IOManager
import managers.CityCollection

class SumOfMetersCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        val sum = collection.sumOfMetersAboveSeaLevel()
        iomanager.outputLine("Сумма высот над уровнем моря для всех городов: $sum м")
    }

    override fun help(): String = "sum_of_meters_above_sea_level - вывести сумму значений поля metersAboveSeaLevel"
}
