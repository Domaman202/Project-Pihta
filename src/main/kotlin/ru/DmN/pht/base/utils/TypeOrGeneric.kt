package ru.DmN.pht.base.utils

import ru.DmN.uu.Unsafe
import java.awt.event.KeyListener
import java.lang.reflect.Type

data class TypeOrGeneric(val generics: Generics, val type: String, val generic: String?) {
    val signature: String
        get(): String = generic ?: type

    fun overridableBy(other: TypeOrGeneric, getType: (type: String) -> VirtualType): Boolean =
        if (this == other)
            true
        else getType(other.type).isAssignableFrom(getType(type))

    override fun equals(other: Any?): Boolean =
        hashCode() == other.hashCode()

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + (generic?.hashCode() ?: 0)
        return result
    }

    companion object {
        fun of(generics: Generics, type: String, vt: VirtualType) =
            if (type.endsWith('^'))
                TypeOrGeneric(generics, vt.name, type.substring(0, type.length - 1))
            else of(generics, vt)
        fun of(generics: Generics, vt: VirtualType): TypeOrGeneric =
            TypeOrGeneric(generics, vt.name, null)
        fun of(generics: Generics, type: Type): TypeOrGeneric =
            getGenericType(type)?.name.let {
                if (it == null)
                    TypeOrGeneric(generics, typeName(type), null)
                else TypeOrGeneric(generics, it, typeName(type))
            }
        fun of(generics: Generics, clazz: Class<*>): TypeOrGeneric =
            TypeOrGeneric(generics, clazz.name, null)

        fun typeName(type: Type): String {
            val i = type.typeName.indexOf('<')
            return if (i == -1)
                type.typeName
            else type.typeName.substring(0, i)
        }

        private fun getGenericType(generic: Type): Klass? {
            return try {
                val cTypeVariableImpl = klassOf("sun.reflect.generics.reflectiveObjects.TypeVariableImpl")
                if (cTypeVariableImpl.isAssignableFrom(generic.javaClass))
                    (cTypeVariableImpl.getMethod("getBounds").apply { Unsafe.forceSetAccessible(this) }.invoke(generic) as Array<Type>)[0]
                        .let { if (it is Klass) it else null }
                else null
            } catch (_: Throwable) { null }
        }
    }
}