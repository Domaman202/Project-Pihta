package ru.DmN.pht.base.compiler.java.utils

enum class CompileStage {
    UNKNOWN,
    MACROS_DEFINE,
    MACROS_IMPORT,
    TYPES_PREDEFINE,
    TYPES_DEFINE,
    METHODS_PREDEFINE,
    FIELDS_DEFINE,
    METHODS_DEFINE,
    EXTENDS_IMPORT,
    METHODS_BODY,
}