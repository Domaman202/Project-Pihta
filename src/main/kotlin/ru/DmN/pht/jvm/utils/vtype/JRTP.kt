package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.siberia.utils.Klass
import ru.DmN.siberia.utils.klassOf
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType
import java.lang.reflect.Modifier
import java.util.*

/**
 * Java Runtime Types Provider
 *
 * Предоставляет типы путём получения классов из ClassLoader-а.
 */
class JRTP : TypesProvider() {
    override fun typeOf(name: String): VirtualType =
        types[name.hashCode()] ?: addType(klassOf(name))

    override fun typeOfOrNull(name: String): VirtualType? =
        try { typeOf(name) } catch (_: ClassNotFoundException) { null }

    private fun typeOf(klass: Klass): VirtualType =
        typeOfOrNull(klass.name) ?: addType(klass)

    private fun addType(klass: Klass): VirtualType {
        val parents: MutableList<VirtualType> = ArrayList()
        klass.superclass?.let { parents.add(typeOf(it.name)) }
        Arrays.stream(klass.interfaces).map { typeOf(it.name) }.forEach(parents::add)
        val fields = ArrayList<VirtualField>()
        val methods = ArrayList<VirtualMethod>()
        return JavaVirtualTypeImpl(
            klass.name,
            parents,
            fields,
            methods,
            klass.componentType?.let(::typeOf),
            klass.isInterface,
            Modifier.isFinal(klass.modifiers) || klass.isEnum
        ).apply {
            this@JRTP += this
            fields += klass.declaredFields.map { VirtualField.of(::typeOf, it) }
            methods += klass.declaredConstructors.map { VirtualMethod.of(::typeOf, it) }
            scanTypeMethods(methods, klass)
            generics += klass.typeParameters.map {
                val bound = it.bounds.lastOrNull()
                Pair(it.name, typeOf(if (bound != null && bound is Klass) bound else Any::class.java))
            }
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