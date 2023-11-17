package ru.DmN.pht.base.utils

import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.ArrayList

abstract class VirtualType {
    abstract val name: String
    //
    abstract val parents: List<VirtualType>
    abstract val fields: List<VirtualField>
    abstract val methods: List<VirtualMethod>
    //
    abstract val componentType: VirtualType?
    //
    abstract val isInterface: Boolean
    abstract val isAbstract: Boolean
    abstract val isFinal: Boolean

    open val simpleName: String
        get() = name.substring(name.lastIndexOf('.') + 1)
    open val className: String
        get() = name.replace('.', '/')
    open val superclass: VirtualType?
        get() = if (isInterface) null else parents.find { !it.isInterface }
    open val interfaces: List<VirtualType>
        get() = if (isInterface) parents else parents.drop(1)
    open val arrayType: VirtualType
        get() = VirtualTypeImpl("[${desc.replace('/', '.')}", componentType = this)
    open val isPrimitive
        get() = name.isPrimitive()
    open val isArray
        get() = componentType != null
    open val desc: String
        get() = if (this.isArray)
            "[${componentType!!.desc}"
        else when (name) {
            "void"      -> "V"
            "boolean"   -> "Z"
            "byte"      -> "B"
            "short"     -> "S"
            "char"      -> "C"
            "int"       -> "I"
            "long"      -> "J"
            "float"     ->"F"
            "double"    -> "D"
            else -> "L$className;"
        }

    open fun isAssignableFrom(target: VirtualType): Boolean =
        if (target.name == this.name || parents.any { it.isAssignableFrom(target) })
            true
        else if (isInterface)
            target == ofKlass(Any::class.java)
        else false

    override fun hashCode(): Int =
        name.hashCode()

    override fun equals(other: Any?): Boolean =
        if (other is VirtualType)
            other.name == name
        else false

    override fun toString(): String {
        return "VT($name)"
    }

    companion object {
        private val TYPES: MutableMap<String, VirtualType> = WeakHashMap()
        val VOID = ofKlass(Void::class.javaPrimitiveType!!)
        val BOOLEAN = ofKlass(Boolean::class.javaPrimitiveType!!)
        val BYTE = ofKlass(Byte::class.javaPrimitiveType!!)
        val SHORT = ofKlass(Short::class.javaPrimitiveType!!)
        val CHAR = ofKlass(Char::class.javaPrimitiveType!!)
        val INT = ofKlass(Int::class.javaPrimitiveType!!)
        val LONG = ofKlass(Long::class.javaPrimitiveType!!)
        val FLOAT = ofKlass(Float::class.javaPrimitiveType!!)
        val DOUBLE = ofKlass(Double::class.javaPrimitiveType!!)

        fun ofKlass(name: String) =
            ofKlass(klassOf(name))

        fun ofKlass(klass: Klass): VirtualType =
            TYPES[klass.name] ?: createOfKlass(klass)

        private fun createOfKlass(klass: Klass): VirtualType =
            VirtualTypeImpl(klass.name).apply {
                TYPES[klass.name] = this
                klass.superclass?.let { parents.add(ofKlass(it.name)) }
                Arrays.stream(klass.interfaces).map { ofKlass(it.name) }.forEach(parents::add)
                componentType = klass.componentType?.let(Companion::ofKlass)
                isInterface = klass.isInterface
                isAbstract = Modifier.isAbstract(klass.modifiers)
                isFinal = Modifier.isFinal(klass.modifiers) || klass.isEnum
                fields += klass.declaredFields.map(VirtualField.Companion::of)
                methods += klass.declaredConstructors.map(VirtualMethod.Companion::of)
                methods += klass.declaredMethods.map(VirtualMethod.Companion::of)
            }
    }

    class VirtualTypeImpl(
        override var name: String,
        //
        override var parents: MutableList<VirtualType> = ArrayList(),
        override var fields: MutableList<VirtualField> = ArrayList(),
        override var methods: MutableList<VirtualMethod> = ArrayList(),
        //
        override var componentType: VirtualType? = null,
        //
        override var isInterface: Boolean = false,
        override var isAbstract: Boolean = false,
        override var isFinal: Boolean = false
    ) : VirtualType()
}