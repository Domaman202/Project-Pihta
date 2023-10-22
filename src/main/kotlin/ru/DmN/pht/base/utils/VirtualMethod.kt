package ru.DmN.pht.base.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Modifier

data class VirtualMethod(
    var declaringClass: VirtualType?,
    var name: String,
    var rettype: VirtualType,
    var argsc: List<VirtualType>,
    var argsn: List<String>,
    val modifiers: MethodModifiers,
    var extend: VirtualType? = null
) {
    val argsDesc: String
        get() {
            val str = StringBuilder()
            argsc.forEach { str.append(it.desc) }
            return str.toString()
        }
    val desc: String
        get() = "($argsDesc)${if (name.startsWith("<")) "V" else rettype.desc}"

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
            return VirtualMethod(
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
            return VirtualMethod(
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
}