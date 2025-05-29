package commands

import main.Orginize.IOManager
import managers.CityCollection
import java.io.IOException

class SaveCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        if (args.isEmpty()) throw IllegalArgumentException("Не указано имя файла")
        val filename = args[0]

        try {
            collection.saveToFile(filename)
            iomanager.outputLine("Коллекция успешно сохранена в файл '$filename'")
        } catch (e: IOException) {
            iomanager.outputLine("Ошибка при сохранении файла: ${e.message}")
        }
    }

    override fun help(): String = "save filename - сохранить коллекцию в файл"
}
