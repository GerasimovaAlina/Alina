package models

data class Human(
    val name: String,
    val height: Long
) {
    init {
        require(name.isNotBlank()) { "Имя не может быть пустым" }
        require(height > 0) { "Рост должен быть положительным" }
    }
}
