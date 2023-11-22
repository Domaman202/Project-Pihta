package ru.DmN.pht.base.utils

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.*
import java.io.DataInputStream
import java.io.InputStream

fun InputStream.readAllBytes(): ByteArray {
    val bytes = ByteArray(available())
    DataInputStream(this).readFully(bytes)
    return bytes
}

fun getJavaClassVersion(): Int {
    val version = getJavaVersion()
    if (version > 7)
        return Opcodes.V1_8 + version - 8
    throw RuntimeException("Unsupported platform!")
}

fun getJavaVersion(): Int {
    var version = System.getProperty("java.version")
    if (version.startsWith("1.")) {
        version = version.substring(2, 3)
    } else {
        val dot = version.indexOf(".")
        if (dot != -1) {
            version = version.substring(0, dot)
        }
    }
    return version.toInt()
}

fun <T> Map<Regex, T>.getRegex(key: String): T? =
    entries.find { key.matches(it.key) }?.value

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

val String.className
    get() = this.replace('.', '/')

fun Parser.nextOpenCBracket(): Token = this.nextToken()!!.checkOpenCBracket()
fun Parser.nextCloseCBracket(): Token = this.nextToken()!!.checkCloseCBracket()
fun Parser.nextOperation(): Token = this.nextToken()!!.checkOperation()
fun Parser.nextType(): Token = this.nextToken()!!.checkType()
fun Parser.nextNaming(): Token = this.nextToken()!!.checkNaming()

typealias Klass = Class<*>

fun klassOf(name: String): Klass =
    if (name.isPrimitive())
        name.getPrimitive()
    else Class.forName(name.let { if (name.startsWith('L') && name.endsWith(';')) name.substring(1, name.length - 1).replace('/', '.') else name }) as Klass

val Klass.desc
    get() = if (name.isPrimitive()) when (name) {
        "void" -> "V"
        "boolean" -> "Z"
        "byte" -> "B"
        "char" -> "C"
        "short" -> "S"
        "int" -> "I"
        "long" -> "J"
        "float" -> "F"
        "double" -> "D"
        else -> throw RuntimeException()
    } else "L${name.replace('.', '/')};"


fun String.getPrimitive(): Klass {
    return when (this) {
        "void" -> Void::class.javaPrimitiveType
        "boolean" -> Boolean::class.javaPrimitiveType
        "byte" -> Byte::class.javaPrimitiveType
        "char" -> Char::class.javaPrimitiveType
        "short" -> Short::class.javaPrimitiveType
        "int" -> Int::class.javaPrimitiveType
        "long" -> Long::class.javaPrimitiveType
        "float" -> Float::class.javaPrimitiveType
        "double" -> Double::class.javaPrimitiveType
        else -> throw RuntimeException()
    } as Klass
}

fun String.isPrimitive(): Boolean {
    return when (this) {
        "void",
        "boolean",
        "byte",
        "char",
        "short",
        "int",
        "long",
        "float",
        "double" -> true

        else -> false
    }
}

fun StringBuilder.indent(indent: Int): StringBuilder {
    this.append("|\t".repeat(indent))
    return this
}
