package commands

import main.Orginize.IOManager
import main.Orginize.ScriptInputManager
import java.io.File

class ExecuteScriptCommand(
    private val commandManager: CommandManager,
    private val iomanager: IOManager
) : Command {
    companion object {
        private val executingScripts = mutableSetOf<String>()
    }

    override fun execute(args: List<String>) {
        if (args.isEmpty()) throw IllegalArgumentException("Не указан файл скрипта")
        val filename = args[0]

        if (executingScripts.contains(filename)) {
            iomanager.outputLine("Предупреждение: обнаружена рекурсия, пропускаем выполнение команды '$filename'")
            return
        }

        try {
            executingScripts.add(filename)
            val lines = File(filename).readLines().iterator()
            val oldInput = iomanager.input
            iomanager.input = ScriptInputManager(lines)
            try {
                while (iomanager.input.hasInput()) {
                    val line = iomanager.input.readLine()?.trim() ?: continue
                    if (line.isEmpty()) continue

                    val parts = line.split("\\s+".toRegex(), 2)
                    val commandName = parts[0]
                    val restOfLine = if (parts.size > 1) parts[1] else ""

                    if (commandName == "add") {
                        val parameters = mutableListOf<String>()
                        parameters.add("script_mode")
                        if (restOfLine.isNotBlank()) {
                            parameters.addAll(restOfLine.split("\\s+".toRegex()))
                        }
                        repeat(12 - (parameters.size - 1)) {
                            if (iomanager.input.hasInput()) {
                                parameters.add(iomanager.input.readLine()!!.trim())
                            } else {
                                throw IllegalArgumentException("Недостаточно параметров для команды add в скрипте")
                            }
                        }
                        commandManager.executeCommand("add " + parameters.joinToString(" "))
                    } else {
                        commandManager.executeCommand(line)
                    }
                }
            } finally {
                iomanager.input = oldInput
                executingScripts.remove(filename)
            }
        } catch (e: Exception) {
            iomanager.outputLine("Ошибка выполнения скрипта '$filename': ${e.message}")
            executingScripts.remove(filename)
        }
    }

    override fun help() = "execute_script filename - выполнить команды из файла"
}