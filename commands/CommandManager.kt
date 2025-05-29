package commands

import main.Orginize.IOManager

class CommandManager(private val iomanager: IOManager) {
    val commands = mutableMapOf<String, Command>()

    fun register(name: String, command: Command) {
        commands[name] = command
    }

    fun executeCommand(commandLine: String) {
        val parts = commandLine.split("\\s+".toRegex())
        if (parts.isNotEmpty()) {
            val commandName = parts[0]
            val args = parts.drop(1)
            commands[commandName]?.execute(args)
                ?: iomanager.outputLine("Команда '$commandName' не найдена")
        }
    }
}
