package main.Orginize
/* читает данные из скрипта*/
class ScriptInputManager(private val lines: Iterator<String>) : InputManager {

    override fun readLine(): String? {
        return if (lines.hasNext()) lines.next() else null
    }

    override fun hasInput(): Boolean {
        return lines.hasNext()
    }
}