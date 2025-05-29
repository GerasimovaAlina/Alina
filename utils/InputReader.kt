package utils
/*считывание различных типов данных с проверкой*/
import main.Orginize.IOManager
import java.util.*

object InputReader {

    fun readString(prompt: String?, defaultValue: String? = null, iomanager: IOManager): String {
        while (true) {
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isNotEmpty() -> return input
                defaultValue != null -> return defaultValue
                else -> iomanager.outputLine("Ошибка: поле не может быть пустым")
            }
        }
    }

    fun readInt(
        iomanager: IOManager,
        prompt: String,
        defaultValue: Int? = null,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE
    ): Int {
        while (true) {
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите число")
                else -> try {
                    val value = input.toInt()
                    if (value !in min..max) {
                        iomanager.outputLine("Ошибка: число должно быть от $min до $max")
                    } else {
                        return value
                    }
                } catch (e: NumberFormatException) {
                    iomanager.outputLine("Ошибка: введите целое число")
                }
            }
        }
    }

    fun readNullableInt(prompt: String?, iomanager: IOManager): Int? {
        while (true) {
            iomanager.outputInline("$prompt (или Enter для пропуска) ")
            val input = iomanager.readLine().trim()
            if (input.isEmpty()) return null
            try {
                return input.toInt()
            } catch (e: NumberFormatException) {
                iomanager.outputLine("Ошибка: введите целое число или оставьте пустым")
            }
        }
    }

    fun readFloat(
        iomanager: IOManager,
        prompt: String?,
        defaultValue: Float? = null,
        min: Float = Float.MIN_VALUE,
        max: Float = Float.MAX_VALUE
    ): Float {
        while (true) {
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите число")
                else -> try {
                    val value = input.toFloat()
                    if (value !in min..max) {
                        iomanager.outputLine("Ошибка: число должно быть от $min до $max")
                    } else {
                        return value
                    }
                } catch (e: NumberFormatException) {
                    iomanager.outputLine("Ошибка: введите число")
                }
            }
        }
    }

    fun readDouble(
        iomanager: IOManager,
        prompt: String?,
        defaultValue: Double? = null,
        min: Double = Double.MIN_VALUE,
        max: Double = Double.MAX_VALUE
    ): Double {
        while (true) {
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите число")
                else -> try {
                    val value = input.toDouble()
                    if (value !in min..max) {
                        iomanager.outputLine("Ошибка: число должно быть от $min до $max")
                    } else {
                        return value
                    }
                } catch (e: NumberFormatException) {
                    iomanager.outputLine("Ошибка: введите число")
                }
            }
        }
    }

    fun readLong(
        prompt: String?, defaultValue: Long? = null, min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE,
        iomanager: IOManager
    ): Long {
        while (true) {
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите число")
                else -> try {
                    val value = input.toLong()
                    if (value !in min..max) {
                        iomanager.outputLine("Ошибка: число должно быть от $min до $max")
                    } else {
                        return value
                    }
                } catch (e: NumberFormatException) {
                    iomanager.outputLine("Ошибка: введите целое число")
                }
            }
        }
    }

    inline fun <reified T : Enum<T>> readEnum(prompt: String, defaultValue: T? = null, iomanager: IOManager): T {
        while (true) {
            iomanager.outputLine("Доступные значения: ${enumValues<T>().joinToString()}")
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите одно из значений")
                else -> try {
                    return enumValueOf<T>(input.uppercase(Locale.getDefault()))
                } catch (e: IllegalArgumentException) {
                    iomanager.outputLine("Ошибка: введите одно из доступных значений")
                }
            }
        }
    }

    fun <T : Enum<T>> readEnum(prompt: String, enumClass: Class<T>, iomanager: IOManager): T {
        while (true) {
            iomanager.outputLine("Доступные значения: ${enumClass.enumConstants.joinToString()}")
            iomanager.outputInline("$prompt ")
            val input = iomanager.readLine().trim().uppercase(Locale.getDefault())
            try {
                return java.lang.Enum.valueOf(enumClass, input)
            } catch (e: IllegalArgumentException) {
                iomanager.outputLine("Ошибка: введите одно из доступных значений")
            }
        }
    }

    fun <T : Enum<T>> readEnum(prompt: String, enumClass: Class<T>, defaultValue: T? = null, iomanager: IOManager): T {
        while (true) {
            iomanager.outputLine("Доступные значения: ${enumClass.enumConstants.joinToString()}")
            iomanager.outputInline("$prompt${defaultValue?.let { " [$it]" } ?: ""} ")
            val input = iomanager.readLine().trim()
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите одно из значений")
                else -> try {
                    return java.lang.Enum.valueOf(enumClass, input.uppercase(Locale.getDefault()))
                } catch (e: IllegalArgumentException) {
                    iomanager.outputLine("Ошибка: введите одно из доступных значений")
                }
            }
        }
    }

    fun readBoolean(prompt: String?, defaultValue: Boolean? = null, iomanager: IOManager): Boolean {
        while (true) {
            val options = when (defaultValue) {
                true -> " [Y/n]"
                false -> " [y/N]"
                null -> " [y/n]"
            }
            iomanager.outputInline("$prompt$options ")
            val input = iomanager.readLine().trim().lowercase(Locale.getDefault())
            when {
                input.isEmpty() && defaultValue != null -> return defaultValue
                input.isEmpty() -> iomanager.outputLine("Ошибка: введите y/n")
                input in setOf("y", "yes") -> return true
                input in setOf("n", "no") -> return false
                else -> iomanager.outputLine("Ошибка: введите y/n")
            }
        }
    }
}
