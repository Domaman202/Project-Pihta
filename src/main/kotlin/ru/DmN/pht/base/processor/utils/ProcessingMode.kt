package ru.DmN.pht.base.processor.utils

data class ProcessingMode(
    val edit: EditType,
    val value: ValType
) {
    fun edit() =
        copy(edit = EditType.EDIT)
    fun copy() =
        copy(edit = EditType.COPY_ON_EDIT)
    fun value() =
        copy(value = ValType.VALUE)
    fun noValue() =
        copy(value = ValType.NO_VALUE)
}
