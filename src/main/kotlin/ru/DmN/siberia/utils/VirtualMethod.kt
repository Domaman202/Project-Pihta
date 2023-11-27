package ru.DmN.siberia.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * Абстрантный виртуальный метод.
 */
abstract class VirtualMethod {
    /**
     * Класс, которому принадлежит поле.
     */
    abstract val declaringClass: VirtualType?

    /**
     * Имя.
     */
    abstract val name: String

    /**
     * Возвращаемый тип.
     */
    abstract val rettype: VirtualType

    /**
     * Типы аргументов.
     */
    abstract val argsc: List<VirtualType>

    /**
     * Имена аргументов.
     */
    abstract val argsn: List<String>

    /**
     * Модификаторы метода.
     */
    abstract val modifiers: MethodModifiers

    /**
     * Расширяемый тип, если таковой имеется, в противном случае null.
     */
    abstract val extend: VirtualType?

    /**
     * Дескриптор аргументов.
     */
    val argsDesc: String
        get() {
            val str = StringBuilder()
            argsc.forEach { str.append(it.desc) }
            return str.toString()
        }

    /**
     * Дескриптор метода.
     */
    val desc: String
        get() = "($argsDesc)${if (name.startsWith("<")) "V" else rettype.desc}"

    override fun hashCode(): Int =
        name.hashCode() + desc.hashCode() + (declaringClass?.hashCode() ?: 0)

    override fun equals(other: Any?): Boolean =
        other is VirtualMethod && other.hashCode() == hashCode()

    companion object {
        /**
         * Создаёт новый метод.
         * Использует typeOf метод для взятия новых типов по имени.
         */
        fun of(typeOf: (name: String) -> VirtualType, ctor: Constructor<*>): VirtualMethod =
            of(typeOf(ctor.declaringClass.name), ctor)

        /**
         * Создаёт новый метод.
         * Использует typeOf метод для взятия новых типов по имени.
         */
        fun of(typeOf: (name: String) -> VirtualType, method: Method): VirtualMethod =
            of(typeOf(method.declaringClass.name), method)

        /**
         * Создаёт новый метод.
         */
        fun of(ctor: Constructor<*>): VirtualMethod =
            of(VirtualType.ofKlass(ctor.declaringClass), ctor)

        /**
         * Создаёт новый метод.
         */
        fun of(method: Method): VirtualMethod =
            of(VirtualType.ofKlass(method.declaringClass), method)

        /**
         * Создаёт новый метод.
         */
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

        /**
         * Создаёт новый метод.
         */
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

    /**
     * Простая реализация виртуального метода.
     */
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