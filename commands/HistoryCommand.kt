package commands

import main.Orginize.IOManager

class HistoryCommand(
    private val history: List<String>,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        if (history.isEmpty()) {
            iomanager.outputLine("История команд пуста")
            return
        }

        iomanager.outputLine("Последние выполненные команды:")
        history.forEachIndexed { index, cmd ->
            iomanager.outputLine("${index + 1}. $cmd")
        }
    }

    override fun help(): String = "history - вывести последние 12 команд (без аргументов)"
}
