package commands

import main.Orginize.IOManager

class ExitCommand(private val iomanager: IOManager) : Command {

    override fun execute(args: List<String>) {
        iomanager.outputLine("Завершение программы")
        System.exit(0)
    }

    override fun help(): String = "exit - завершить программу (без сохранения в файл)"
}
