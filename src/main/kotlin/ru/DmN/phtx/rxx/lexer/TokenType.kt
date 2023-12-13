package ru.DmN.phtx.rxx.lexer

import ru.DmN.siberia.lexer.Token

enum class TokenType : Token.IType {
    // :
    COLON,
    // ,
    COMMA,
    // .
    DOT,
    // `Слово`
    WORD
}