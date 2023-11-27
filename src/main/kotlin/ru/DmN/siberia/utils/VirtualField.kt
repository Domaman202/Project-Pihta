package ru.DmN.siberia.utils

import java.lang.reflect.Field
import java.lang.reflect.Modifier

abstract class VirtualField {
    abstract val declaringClass: VirtualType?
    abstract val name: String
    abstract val type: VirtualType
    abstract val isStatic: Boolean
    abstract val isEnum: Boolean
    val desc
        get() = type.desc

    override fun hashCode(): Int =
        name.hashCode() + type.hashCode() + (declaringClass?.hashCode() ?: 0)

    override fun equals(other: Any?): Boolean =
        other is VirtualField && other.hashCode() == hashCode()

    companion object {
        fun of(typeOf: (name: String) -> VirtualType, field: Field): VirtualField =
            VirtualFieldImpl(typeOf(field.declaringClass.name), field.name, typeOf(field.type.name), Modifier.isStatic(field.modifiers), field.isEnumConstant)
        fun of(field: Field): VirtualField =
            VirtualFieldImpl(VirtualType.ofKlass(field.type), field.name, VirtualType.ofKlass(field.type), Modifier.isStatic(field.modifiers), field.isEnumConstant)
    }

    class VirtualFieldImpl(
        override var declaringClass: VirtualType?,
        override var name: String,
        override var type: VirtualType,
        override var isStatic: Boolean,
        override var isEnum: Boolean
    ) : VirtualField()
}