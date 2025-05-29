package main.Orginize
/*интерфейс для управления вводом данных*/

interface InputManager {
    fun readLine(): String?
    fun hasInput(): Boolean
}
