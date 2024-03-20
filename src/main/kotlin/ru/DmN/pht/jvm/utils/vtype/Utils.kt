package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

/**
 * Имя в представлении JVM.
 */
inline val VirtualType.jvmName: String
    get() = if (this is IJvmVirtualType) this.jvmName else this.name.replace('.', '/')

/**
 * Родительский класс, если тип это класс, иначе null.
 */
inline val VirtualType.superclass: VirtualType?
    get() = if (this.isInterface) null else this.parents.find { !it.isInterface }

/**
 * Реализуемые интерфейсы.
 */
inline val VirtualType.interfaces: List<VirtualType>
    get() = if (this.isInterface) this.parents else this.parents.drop(1)

/**
 * Дескриптор.
 */
val VirtualType.desc: String
    get() =
        if (this.isArray)
            "[${this.componentType!!.desc}"
        else when (this.name) {
            "void" -> "V"
            "boolean" -> "Z"
            "byte" -> "B"
            "short" -> "S"
            "char" -> "C"
            "int" -> "I"
            "long" -> "J"
            "float" -> "F"
            "double" -> "D"
            else -> "L${this.jvmName};"
        }


/**
 * Сигнатура.
 */
val VirtualType.signature: String?
    get() =
        if (this.generics.isEmpty())
            null
        else {
            val sb = StringBuilder().append('<')
            this.generics.forEach { (k, v) -> sb.append(k).append(':').append(v.desc) }
            sb.append('>').append(this.superclass!!.desc).toString()
        }

/**
 * Generic's (Name / Type)
 */
val VirtualType.generics: Map<String, VirtualType>
    get() = if (this is PhtVirtualType) this.generics else emptyMap()

/**
 * Дескриптор.
 */
inline val VirtualField.desc
    get() = this.type.desc

/**
 * Дескриптор аргументов.
 */
inline val VirtualMethod.argsDesc: String
    get() {
        val str = StringBuilder()
        this.argsc.forEach { str.append(it.desc) }
        return str.toString()
    }

/**
 * Дескриптор.
 */
val VirtualMethod.desc: String
    get() = "(${this.argsDesc})${if (this.name.startsWith("<")) "V" else this.rettype.desc}"

/**
 * Сигнатура.
 */
val VirtualMethod.signature: String?
    get() =
        if (this.generics.isEmpty())
            null
        else {
            val sb = StringBuilder()
            if (!this.modifiers.static) {
                val list = this.generics.entries.drop(this.declaringClass.generics.size)
                if (list.isNotEmpty()) {
                    sb.append('<')
                    list.forEach {
                        sb.append(it.key).append(':').append(it.value.desc)
                    }
                    sb.append('>')
                }
            }
            sb.append('(')
            this.argsg.forEach { sb.append('T').append(it).append(';') }
            sb.append(')').append(retgen?.let { "T${retgen};" } ?: rettype.desc).toString()
        }


/**
 * Generic's (Name / Type)
 */
val VirtualMethod.generics: Map<String, VirtualType>
    get() = if (this is PhtVirtualMethod) this.generics else emptyMap()