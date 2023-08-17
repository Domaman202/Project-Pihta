package ru.DmN.pht.base.compiler.java

enum class CompileStage {
    TYPES_PREDEFINE,
    TYPES_DEFINE,
    METHODS_PREDEFINE,
    METHODS_DEFINE,
    EXTENDS_IMPORT,
    METHODS_BODY,
}