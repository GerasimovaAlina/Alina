package main

import commands.*
import managers.CityCollection
import main.Orginize.ConsoleInputManager
import main.Orginize.ConsoleOutputManager
import main.Orginize.IOManager

fun main(args: Array<String>) {
    val consoleInputManager = ConsoleInputManager()
    val consoleOutputManager = ConsoleOutputManager()
    val ioManager = IOManager(consoleInputManager, consoleOutputManager)

    val cityCollection = CityCollection()

    val commandHistory = mutableListOf<String>()
    val MAX_HISTORY_SIZE = 12

    if (args.isNotEmpty()) {
        try {
            cityCollection.loadFromFile(args[0])
            ioManager.outputLine("Коллекция загружена из файла: ${args[0]}")
        } catch (e: Exception) {
            ioManager.outputLine("Ошибка загрузки файла: ${e.message}")
        }
    } else {
        loadCollectionFromUserInput(cityCollection, ioManager)
    }

    val commandManager = CommandManager(ioManager).apply {
        register("help", HelpCommand(this, ioManager))
        register("info", InfoCommand(cityCollection, ioManager))
        register("show", ShowCommand(cityCollection, ioManager))
        register("add", AddCommand(cityCollection, ioManager))
        register("update", UpdateCommand(cityCollection, ioManager))
        register("remove_by_id", RemoveByIdCommand(cityCollection, ioManager))
        register("clear", ClearCommand(cityCollection, ioManager))
        register("save", SaveCommand(cityCollection, ioManager))
        register("execute_script", ExecuteScriptCommand(this, ioManager))
        register("exit", ExitCommand(ioManager))
        register("remove_greater", RemoveGreaterCommand(cityCollection, ioManager))
        register("remove_lower", RemoveLowerCommand(cityCollection, ioManager))
        register("sum_of_meters_above_sea_level", SumOfMetersCommand(cityCollection, ioManager))
        register("min_by_creation_date", MinByCreationDateCommand(cityCollection, ioManager))
        register("count_less_than_meters_above_sea_level", CountLessThanMetersCommand(cityCollection, ioManager))
        register("history", HistoryCommand(commandHistory, ioManager))
    }

    ioManager.outputLine("""
        |Программа управления коллекцией городов.
        |Доступные команды: help, info, show, add, update, remove_by_id,
        |clear, save, execute_script, exit, remove_greater, remove_lower,
        |sum_of_meters_above_sea_level, min_by_creation_date,
        |count_less_than_meters_above_sea_level, history
        |Введите 'help' для получения справки.
    """.trimMargin())

    while (true) {
        try {
            ioManager.outputInline("> ")
            val input = ioManager.readLine().trim()
            if (input.isNotEmpty()) {
                if (!input.startsWith("history")) {
                    commandHistory.add(input)
                    if (commandHistory.size > MAX_HISTORY_SIZE) {
                        commandHistory.removeAt(0)
                    }
                }
                commandManager.executeCommand(input)
            }
        } catch (e: Exception) {
            ioManager.outputLine("Ошибка: ${e.message}")
        }
    }
}

private fun loadCollectionFromUserInput(cityCollection: CityCollection, ioManager: IOManager) {
    ioManager.outputLine("Файл для загрузки не указан в аргументах командной строки.")
    ioManager.outputInline("Введите имя файла для загрузки коллекции (или нажмите Enter для пустой коллекции): ")
    val fileName = ioManager.readLine().trim()
    if (fileName.isNotEmpty()) {
        try {
            cityCollection.loadFromFile(fileName)
            ioManager.outputLine("Коллекция загружена из файла: $fileName")
        } catch (e: Exception) {
            ioManager.outputLine("Ошибка загрузки файла: ${e.message}")
            ioManager.outputLine("Будет создана пустая коллекция.")
        }
    } else {
        ioManager.outputLine("Будет создана пустая коллекция.")
    }
}
