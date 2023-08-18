package ru.DmN.pht.base.compiler.java.utils

enum class CompileStage {
    MACROS_DEFINE_IMPORT,
    TYPES_PREDEFINE,
    TYPES_DEFINE,
    METHODS_PREDEFINE,
    METHODS_DEFINE,
    EXTENDS_IMPORT,
    METHODS_BODY,
}