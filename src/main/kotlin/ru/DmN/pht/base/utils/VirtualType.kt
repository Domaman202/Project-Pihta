package ru.DmN.pht.base.utils

import java.lang.reflect.Modifier
import java.util.*
import kotlin.collections.ArrayList

class VirtualType(
    var name: String,
    //
    var parents: MutableList<VirtualType> = ArrayList(),
    var fields: MutableList<VirtualField> = ArrayList(),
    var methods: MutableList<VirtualMethod> = ArrayList(),
    //
    var componentType: VirtualType? = null,
    //
    var isInterface: Boolean = false,
    var final: Boolean = false,
    //
    var generics: Generics = Generics()
) {
    val className: String
        get() = name.replace('.', '/')
    val superclass: VirtualType?
        get() = if (isInterface) null else parents.find { !it.isInterface }
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
            "void" -> "V"
            "boolean" -> "Z"
            "byte" -> "B"
            "short" -> "S"
            "char" -> "C"
            "int" -> "I"
            "long" -> "J"
            "double" -> "D"
            else -> "L$className;"
        }

    fun getAllMethods(): MutableList<VirtualMethod> {
        val list = ArrayList<VirtualMethod>()
        list += methods
        parents.forEach { list += it.methods }
        return list
    }

    fun isAssignableFrom(target: VirtualType): Boolean =
        if (target.name == this.name)
            true
        else parents.any { it.isAssignableFrom(target) }

    override fun toString(): String {
        return "VT($name)"
    }

    fun with(generics: Generics) =
        VirtualType(name, parents, fields, methods, componentType, isInterface, final, generics)

    companion object {
        private val TYPES: MutableMap<String, VirtualType> = WeakHashMap()

        fun ofKlass(name: String) =
            ofKlass(klassOf(name))

        fun ofKlass(klass: Klass): VirtualType =
            TYPES[klass.name] ?: createOfKlass(klass)

        private fun createOfKlass(klass: Klass): VirtualType =
            VirtualType(klass.name).apply {
                TYPES[klass.name] = this
                klass.superclass?.let { parents.add(ofKlass(it.name)) }
                Arrays.stream(klass.interfaces).map { ofKlass(it.name) }.forEach(parents::add)
                fields += klass.declaredFields.map(VirtualField.Companion::of)
                methods += klass.declaredConstructors.map(VirtualMethod.Companion::of) +
                        klass.declaredMethods.map(VirtualMethod.Companion::of)
                componentType = klass.componentType?.let(Companion::ofKlass)
                isInterface = klass.isInterface
                final = Modifier.isFinal(klass.modifiers) || klass.isEnum
            }
    }
}