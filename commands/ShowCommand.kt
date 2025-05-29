package commands

import main.Orginize.IOManager
import managers.CityCollection

class ShowCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        val cities = collection.getAll()
        if (cities.isEmpty()) {
            iomanager.outputLine("Коллекция пуста")
            return
        }

        iomanager.outputLine("Элементы коллекции (${cities.size}):")
        cities.forEachIndexed { index, city ->
            iomanager.outputLine("${index + 1}. $city")
        }
    }

    override fun help(): String = "show - вывести все элементы коллекции"
}
