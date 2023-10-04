package ru.DmN.pht.base.utils

import java.lang.reflect.Field
import java.lang.reflect.Modifier

data class VirtualField(
    var declaringClass: VirtualType?,
    var name: String,
    var type: VirtualType,
    var static: Boolean,
    var enum: Boolean
) {
    val desc
        get() = type.desc

    companion object {
        fun of(typeOf: (name: String) -> VirtualType, field: Field): VirtualField =
            VirtualField(typeOf(field.declaringClass.name), field.name, typeOf(field.type.name), Modifier.isStatic(field.modifiers), field.isEnumConstant)
        fun of(field: Field): VirtualField =
            VirtualField(VirtualType.ofKlass(field.type), field.name, VirtualType.ofKlass(field.type), Modifier.isStatic(field.modifiers), field.isEnumConstant)
    }
}