package ru.DmN.siberia.processor.utils

enum class ProcessingStage {
    UNKNOWN,
    MODULE_POST_INIT,
    MACROS_DEFINE,
    MACROS_IMPORT,
    TYPES_IMPORT,
    TYPES_PREDEFINE,
    TYPES_DEFINE,
    EXTENDS_IMPORT,
    METHODS_BODY
}