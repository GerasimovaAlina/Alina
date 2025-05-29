package models

enum class Government {
    ANARCHY,
    MERITOCRACY,
    STRATOCRACY;

    companion object {
        fun valuesString(): String = values().joinToString()
    }
}
