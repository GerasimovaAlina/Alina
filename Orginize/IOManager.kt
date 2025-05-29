package main.Orginize

class IOManager(
    var input: InputManager,
    private val output: OutputManager
) {
    fun readLine(): String = input.readLine() ?: ""
    fun outputLine(text: String) = output.writeLine(text)
    fun outputInline(text: String) = output.write(text)
}
