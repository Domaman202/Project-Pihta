package ru.DmN.pht.base.utils

import ru.DmN.uu.Unsafe
import java.lang.reflect.Type

data class TypeOrGeneric(val _type: String?, val generic: String?) {
    val type: String
        get(): String = generic ?: _type!!

    fun overridableBy(other: TypeOrGeneric, getType: (name: String) -> VirtualType): Boolean =
        if (this == other)
            true
        else getType(other.type).isAssignableFrom(getType(this.type))

    override fun equals(other: Any?): Boolean =
        if (other is TypeOrGeneric)
            other.type == type
        else false

    companion object {
        fun of(type: Type): TypeOrGeneric =
            TypeOrGeneric(type.typeName, getGenericType(type)?.name)
        fun of(vt: VirtualType): TypeOrGeneric =
            TypeOrGeneric(vt.name, null)
        fun of(clazz: Class<*>): TypeOrGeneric =
            TypeOrGeneric(clazz.name, null)

        fun getGenericType(generic: Type): Class<*>? {
            return try {
                val cTypeVariableImpl = Class.forName("sun.reflect.generics.reflectiveObjects.TypeVariableImpl")
                if (cTypeVariableImpl.isAssignableFrom(generic.javaClass))
                    (cTypeVariableImpl.getMethod("getBounds").apply { Unsafe.forceSetAccessible(this) }.invoke(generic) as Array<Type>)[0] as Class<*>
                else null
            } catch (_: Throwable) { null }
        }
    }
}