package ru.DmN.pht.base.utils

import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.ArrayList

data class VirtualType(
    var name: String,
    //
    var parents: MutableList<VirtualType> = ArrayList(),
    var fields: MutableList<VirtualField> = ArrayList(),
    var methods: MutableList<VirtualMethod> = ArrayList(),
    //
    var componentType: VirtualType? = null,
    //
    var isInterface: Boolean = false,
    var abstract: Boolean = false,
    var final: Boolean = false
) {
    val simpleName: String
        get() = name.substring(name.lastIndexOf('.') + 1)
    val className: String
        get() = name.replace('.', '/')
    val superclass: VirtualType?
        get() = if (isInterface) null else parents.find { !it.isInterface }
    val interfaces: List<VirtualType>
        get() = if (isInterface) parents else parents.drop(1)
    val arrayType: VirtualType
        get() = VirtualType("[${desc.replace('/', '.')}", componentType = this)
    val isPrimitive
        get() = name.isPrimitive()
    val isArray
        get() = componentType != null
    val desc: String
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

    fun isAssignableFrom(target: VirtualType): Boolean =
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

    fun copyMethodsTo(to: VirtualType) {
        to.methods += this.methods
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
            VirtualType(klass.name).apply {
                TYPES[klass.name] = this
                klass.superclass?.let { parents.add(ofKlass(it.name)) }
                Arrays.stream(klass.interfaces).map { ofKlass(it.name) }.forEach(parents::add)
                componentType = klass.componentType?.let(Companion::ofKlass)
                isInterface = klass.isInterface
                abstract = Modifier.isAbstract(klass.modifiers)
                final = Modifier.isFinal(klass.modifiers) || klass.isEnum
                fields += klass.declaredFields.map(VirtualField.Companion::of)
                methods += klass.declaredConstructors.map(VirtualMethod.Companion::of)
                methods += klass.declaredMethods.map(VirtualMethod.Companion::of)
//                scanMethods(methods, klass)
            }

        private fun scanMethods(list: MutableList<VirtualMethod>, klass: Klass) {
            list += klass.declaredMethods.map(VirtualMethod.Companion::of)
            if (klass.superclass == null)
                return
            scanMethods(list, klass.superclass)
            klass.interfaces.forEach { scanMethods(list, it) }
        }
    }
}