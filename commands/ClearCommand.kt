package commands

import main.Orginize.IOManager
import managers.CityCollection

class ClearCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        collection.clear()
        iomanager.outputLine("Коллекция успешно очищена")
    }

    override fun help(): String = "clear - очистить коллекцию"
}
