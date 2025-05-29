package commands

import main.Orginize.IOManager

class HelpCommand(
    private val commandManager: CommandManager,
    private val iomanager: IOManager
) : Command {

    override fun execute(args: List<String>) {
        iomanager.outputLine("Доступные команды:")
        iomanager.outputLine("=".repeat(50))

        commandManager.commands.forEach { (name, cmd) ->
            iomanager.outputLine("${name.padEnd(60)} - ${cmd.help()}")
        }

        iomanager.outputLine("\nФормат ввода:")
        iomanager.outputLine("• Простые команды: 'help', 'clear', 'exit'")
        iomanager.outputLine("• Команды с аргументами: 'remove_by_id 5', 'execute_script file.txt'")
        iomanager.outputLine("• Составные данные (как в 'add') вводятся построчно")
    }

    override fun help(): String = "help - вывести справку по доступным командам"
}
