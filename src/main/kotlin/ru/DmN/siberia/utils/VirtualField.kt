package ru.DmN.siberia.utils

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Абстрактное виртуальное поле.
 */
abstract class VirtualField {
    /**
     * Класс, которому принадлежит поле.
     */
    abstract val declaringClass: VirtualType?

    /**
     * Имя.
     */
    abstract val name: String

    /**
     * Тип.
     */
    abstract val type: VirtualType

    /**
     * Статическое ли поле?
     */
    abstract val isStatic: Boolean

    /**
     * Является ли поле instance-ом enum-а?
     */
    abstract val isEnum: Boolean

    /**
     * Дескриптор поля.
     */
    val desc
        get() = type.desc

    override fun hashCode(): Int =
        name.hashCode() + type.hashCode() + (declaringClass?.hashCode() ?: 0)

    override fun equals(other: Any?): Boolean =
        other is VirtualField && other.hashCode() == hashCode()

    companion object {
        /**
         * Создаёт новое поле.
         * Использует typeOf метод для взятия новых типов по имени.
         */
        fun of(typeOf: (name: String) -> VirtualType, field: Field): VirtualField =
            VirtualFieldImpl(typeOf(field.declaringClass.name), field.name, typeOf(field.type.name), Modifier.isStatic(field.modifiers), field.isEnumConstant)

        /**
         * Создаёт новое поле.
         */
        fun of(field: Field): VirtualField =
            VirtualFieldImpl(VirtualType.ofKlass(field.type), field.name, VirtualType.ofKlass(field.type), Modifier.isStatic(field.modifiers), field.isEnumConstant)
    }

    /**
     * Простая реализация виртуального поля.
     */
    class VirtualFieldImpl(
        override var declaringClass: VirtualType?,
        override var name: String,
        override var type: VirtualType,
        override var isStatic: Boolean,
        override var isEnum: Boolean
    ) : VirtualField()
}