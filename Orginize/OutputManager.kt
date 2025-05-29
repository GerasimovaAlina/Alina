package main.Orginize
/*интерфейс для вывода сообщений*/
interface OutputManager {
    fun write(text: String)
    fun writeLine(text: String)
    fun error(text: String)
}
