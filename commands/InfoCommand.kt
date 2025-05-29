package commands

import main.Orginize.IOManager
import managers.CityCollection

class InfoCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        iomanager.outputLine(collection.getInfo())
    }

    override fun help(): String = "info - вывести информацию о коллекции"
}
