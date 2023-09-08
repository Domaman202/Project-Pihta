package ru.DmN.pht.base.utils

import java.lang.reflect.Modifier
import java.util.*

class TypesProvider(val typeOf: (name: String) -> VirtualType) {
    val types: MutableList<VirtualType> = ArrayList()

    fun typeOf(klass: Klass): VirtualType =
        types.find { it.name == klass.name } ?: addType(klass)

    fun typeOrNull(name: String): VirtualType? {
        val type = types.find { it.name == name }
        return if (type == null && name.startsWith('['))
            typeOrNull(name.substring(1))?.let {  VirtualType(name, componentType = it) }
        else type
    }

    fun addType(klass: Klass): VirtualType {
        val parents: MutableList<VirtualType> = ArrayList()
        klass.superclass?.let { parents.add(typeOf(it.name)) }
        Arrays.stream(klass.interfaces).map { typeOf(it.name) }.forEach(parents::add)
        val fields = ArrayList<VirtualField>()
        val methods = ArrayList<VirtualMethod>()
        return VirtualType(
            klass.name,
            parents,
            fields,
            methods,
            componentType = klass.componentType?.let(::typeOf),
            isInterface = klass.isInterface,
            final = Modifier.isFinal(klass.modifiers) || klass.isEnum
        ).apply {
            types += this
            fields += klass.declaredFields.map { VirtualField.of(typeOf, it) }
            methods += klass.declaredConstructors.map { VirtualMethod.of(typeOf, it) } +
                    klass.declaredMethods.map { VirtualMethod.of(typeOf, it) }
        }
    }
}