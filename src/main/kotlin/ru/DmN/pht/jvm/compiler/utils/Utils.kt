package ru.DmN.pht.jvm.compiler.utils

import ru.DmN.siberia.utils.isPrimitive


/**
 * Возвращает дексриптор типа.
 */
val String.desc
    get() = when (this) {
        "void" -> "V"
        "boolean" -> "Z"
        "byte" -> "B"
        "short" -> "S"
        "char" -> "C"
        "int" -> "I"
        "long" -> "J"
        "double" -> "D"
        else -> {
            if (this[0] == '[') {
                var i = 0
                while (this[i] == '[') i++
                val clazz = this.substring(i)
                if (this[1] == 'L' || clazz.isPrimitive())
                    this.className
                else "${this.substring(0, i)}L${clazz.className};"
            }
            else "L${this.className};"
        }
    }

/**
 * Переводит тип в имя класса.
 */
val String.className
    get() = this.replace('.', '/')