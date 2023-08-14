package ru.DmN.pht.base.utils

import ru.DmN.pht.base.compiler.java.Compiler
import java.lang.reflect.Field
import java.lang.reflect.Modifier

data class VirtualField(var name: String, var type: VirtualType, var static: Boolean, var enum: Boolean) {
    val desc
        get() = type.desc

    companion object {
        fun of(compiler: Compiler, field: Field): VirtualField =
            VirtualField(field.name, compiler.typeOf(field.type), Modifier.isStatic(field.modifiers), field.isEnumConstant)
        fun of(field: Field): VirtualField =
            VirtualField(field.name, VirtualType.ofKlass(field.type), Modifier.isStatic(field.modifiers), field.isEnumConstant)
    }
}