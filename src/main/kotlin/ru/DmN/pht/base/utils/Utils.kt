package ru.DmN.pht.base.utils

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.*

fun Parser.nextOpenCBracket(): Token = this.nextToken()!!.checkOpenCBracket()
fun Parser.nextCloseCBracket(): Token = this.nextToken()!!.checkCloseCBracket()
fun Parser.nextOperation(): Token = this.nextToken()!!.checkOperation()
fun Parser.nextType(): Token = this.nextToken()!!.checkType()
fun Parser.nextNaming(): Token = this.nextToken()!!.checkNaming()

typealias Klass = Class<*>

fun klassOf(name: String): Klass =
    if (name.isPrimitive())
        name.getPrimitive()
    else Class.forName(name) as Klass

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
