package ru.DmN.pht.base.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Modifier

abstract class VirtualMethod{
    abstract val declaringClass: VirtualType?
    abstract val name: String
    abstract val rettype: VirtualType
    abstract val argsc: List<VirtualType>
    abstract val argsn: List<String>
    abstract val modifiers: MethodModifiers
    abstract val extend: VirtualType?

    val argsDesc: String
        get() {
            val str = StringBuilder()
            argsc.forEach { str.append(it.desc) }
            return str.toString()
        }
    val desc: String
        get() = "($argsDesc)${if (name.startsWith("<")) "V" else rettype.desc}"

    override fun hashCode(): Int =
        name.hashCode() + desc.hashCode() + (declaringClass?.hashCode() ?: 0)

    companion object {
        fun of(typeOf: (name: String) -> VirtualType, ctor: Constructor<*>): VirtualMethod =
            of(typeOf(ctor.declaringClass.name), ctor)

        fun of(typeOf: (name: String) -> VirtualType, method: Method): VirtualMethod =
            of(typeOf(method.declaringClass.name), method)

        fun of(ctor: Constructor<*>): VirtualMethod =
            of(VirtualType.ofKlass(ctor.declaringClass), ctor)

        fun of(method: Method): VirtualMethod =
            of(VirtualType.ofKlass(method.declaringClass), method)

        private fun of(declaringClass: VirtualType, method: Constructor<*>): VirtualMethod {
            val argsc = ArrayList<VirtualType>()
            val argsn = ArrayList<String>()
            method.parameters.forEach {
                argsc += VirtualType.ofKlass(it.type)
                argsn += it.name
            }
            return VirtualMethodImpl(
                declaringClass,
                "<init>",
                VirtualType.VOID,
                argsc,
                argsn,
                MethodModifiers(
                    varargs = method.isVarArgs,
                    static = Modifier.isStatic(method.modifiers),
                    abstract = method.declaringClass.isInterface
                ),
                null
            )
        }

        private fun of(declaringClass: VirtualType, method: Method): VirtualMethod {
            val argsc = ArrayList<VirtualType>()
            val argsn = ArrayList<String>()
            method.parameters.forEach {
                argsc += VirtualType.ofKlass(it.type)
                argsn += it.name
            }
            return VirtualMethodImpl(
                declaringClass,
                method.name,
                VirtualType.ofKlass(method.returnType),
                argsc,
                argsn,
                MethodModifiers(
                    varargs = method.isVarArgs,
                    static = Modifier.isStatic(method.modifiers),
                    abstract = method.declaringClass.isInterface
                ),
                null
            )
        }
    }

    class VirtualMethodImpl(
        override var declaringClass: VirtualType?,
        override var name: String,
        override var rettype: VirtualType,
        override var argsc: List<VirtualType>,
        override var argsn: List<String>,
        override var modifiers: MethodModifiers,
        override var extend: VirtualType? = null
    ) : VirtualMethod()
}