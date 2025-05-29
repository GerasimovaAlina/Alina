package commands

import main.Orginize.IOManager
import managers.CityCollection

class MinByCreationDateCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        val city = collection.minByCreationDate()
            ?: throw IllegalStateException("Коллекция пуста")
        iomanager.outputLine("Город с самой ранней датой создания:\n$city")
    }

    override fun help(): String = "min_by_creation_date - вывести элемент с минимальной датой создания"
}
