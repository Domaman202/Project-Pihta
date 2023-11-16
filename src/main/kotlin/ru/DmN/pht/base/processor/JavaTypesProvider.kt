package ru.DmN.pht.base.processor

import ru.DmN.pht.base.utils.*
import ru.DmN.pht.base.utils.VirtualType.VirtualTypeImpl
import java.lang.reflect.Modifier
import java.util.*

class JavaTypesProvider : TypesProvider() {
    override fun typeOf(name: String): VirtualType =
        types.find { it.name == name } ?: addType(klassOf(name))

    fun typeOf(klass: Klass): VirtualType =
        types.find { it.name == klass.name } ?: addType(klass)

    private fun addType(klass: Klass): VirtualType {
        val parents: MutableList<VirtualType> = ArrayList()
        klass.superclass?.let { parents.add(typeOf(it.name)) }
        Arrays.stream(klass.interfaces).map { typeOf(it.name) }.forEach(parents::add)
        val fields = ArrayList<VirtualField>()
        val methods = ArrayList<VirtualMethod>()
        return VirtualTypeImpl(
            klass.name,
            parents,
            fields,
            methods,
            componentType = klass.componentType?.let(::typeOf),
            isInterface = klass.isInterface,
            isFinal = Modifier.isFinal(klass.modifiers) || klass.isEnum
        ).apply {
            types += this
            fields += klass.declaredFields.map { VirtualField.of(::typeOf, it) }
            methods += klass.declaredConstructors.map { VirtualMethod.of(::typeOf, it) }
            scanTypeMethods(methods, klass)
        }
    }

    private fun scanTypeMethods(list: MutableList<VirtualMethod>, klass: Klass) {
        list += klass.declaredMethods.map { VirtualMethod.of(::typeOf, it) }
        if (klass.superclass == null)
            return
        scanTypeMethods(list, klass.superclass)
        klass.interfaces.forEach { scanTypeMethods(list, it) }
    }
}