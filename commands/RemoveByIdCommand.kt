package commands

import main.Orginize.IOManager
import managers.CityCollection

class RemoveByIdCommand(
    private val collection: CityCollection,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        if (args.isEmpty()) throw IllegalArgumentException("Не указан ID города")
        val id = args[0].toLongOrNull() ?: throw IllegalArgumentException("Некорректный ID")

        if (collection.removeById(id)) {
            iomanager.outputLine("Город с ID $id успешно удален")
        } else {
            iomanager.outputLine("Город с ID $id не найден")
        }
    }

    override fun help(): String = "remove_by_id id - удалить элемент из коллекции по его id"
}
