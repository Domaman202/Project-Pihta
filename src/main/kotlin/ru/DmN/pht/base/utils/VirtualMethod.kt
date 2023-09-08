package ru.DmN.pht.base.utils

import ru.DmN.pht.base.compiler.java.Compiler
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Modifier

data class VirtualMethod(
    var declaringClass: VirtualType,
    var name: String,
    var rettype: TypeOrGeneric,
    var argsc: List<TypeOrGeneric>,
    var argsn: List<String>,
    var varargs: Boolean = false,
    var static: Boolean = false,
    var abstract: Boolean = false,
    var extend: VirtualType? = null,
    var override: VirtualMethod? = null,
    var generics: Generics = Generics()
) {
    val argsDesc: String
        get() {
            val str = StringBuilder()
            argsc.forEach { str.append(it.type.desc) }
            return str.toString()
        }
    val desc: String
        get() = "($argsDesc)${if (name.startsWith("<")) "V" else rettype.type.desc}"

    fun overrideOrThis(): VirtualMethod =
        override?.overrideOrThis() ?: this

    fun overridableBy(method: VirtualMethod, getType: (name: String) -> VirtualType): Boolean =
        if (!static && !method.static && extend == null && name == method.name && varargs == method.varargs && argsc.size == method.argsc.size) {
            if (rettype.overridableBy(method.rettype, getType)) {
                var j = 0
                argsc.forEachIndexed { i, it -> if (it.overridableBy(method.argsc[i], getType)) j++ }
                j == method.argsc.size
            } else false
        } else false

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
            val generics = Generics()
            val argsc = ArrayList<TypeOrGeneric>()
            val argsn = ArrayList<String>()
            if (declaringClass.final) {
                method.parameters.forEach {
                    argsc += TypeOrGeneric.of(generics, it.type)
                    argsn += it.name
                }
            } else {
                val gpt = method.genericParameterTypes
                method.parameters.forEachIndexed { i, it ->
                    argsc += TypeOrGeneric.of(generics, gpt[i])
                    argsn += it.name
                }
            }
            return VirtualMethod(
                declaringClass,
                "<init>",
                TypeOrGeneric.of(generics, VirtualType.VOID),
                argsc,
                argsn,
                method.isVarArgs,
                Modifier.isStatic(method.modifiers),
                method.declaringClass.isInterface,
                null,
                null, // todo:
                generics
            )
        }

        private fun of(declaringClass: VirtualType, method: Method): VirtualMethod {
            val generics = Generics()
            val argsc = ArrayList<TypeOrGeneric>()
            val argsn = ArrayList<String>()
            if (declaringClass.final) {
                method.parameters.forEach {
                    argsc += TypeOrGeneric.of(generics, it.type)
                    argsn += it.name
                }
            } else {
                val gpt = method.genericParameterTypes
                method.parameters.forEachIndexed { i, it ->
                    argsc += TypeOrGeneric.of(generics, gpt[i])
                    argsn += it.name
                }
            }
            return VirtualMethod(
                declaringClass,
                method.name,
                TypeOrGeneric.of(generics, method.genericReturnType),
                argsc,
                argsn,
                method.isVarArgs,
                Modifier.isStatic(method.modifiers),
                method.declaringClass.isInterface,
                null,
                null, // todo:
                generics
            )
        }
    }
}