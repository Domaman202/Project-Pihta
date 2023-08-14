package ru.DmN.pht.base.lexer

fun Token.checkOpenCBracket(): Token = if (isOpenCBracket()) this else throw RuntimeException()
fun Token.checkCloseCBracket(): Token = if (isCloseCBracket()) this else throw RuntimeException()
fun Token.checkOperation(): Token = if (isOperation()) this else throw RuntimeException()
fun Token.checkType(): Token = if (isType()) this else throw RuntimeException()
fun Token.checkNaming(): Token = if (isNaming()) this else throw RuntimeException()

fun Token.isOpenCBracket() = type == Token.Type.OPEN_CBRACKET
fun Token.isCloseCBracket() = type == Token.Type.CLOSE_CBRACKET
fun Token.isOperation() = type == Token.Type.OPERATION
fun Token.isType() = type == Token.Type.PRIMITIVE || type == Token.Type.CLASS
fun Token.isNaming() = type == Token.Type.NAMING