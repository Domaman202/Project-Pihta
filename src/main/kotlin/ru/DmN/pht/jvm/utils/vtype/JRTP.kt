package ru.DmN.pht.jvm.utils.vtype

import ru.DmN.pht.utils.vtype.PhtVirtualMethod
import ru.DmN.siberia.utils.Klass
import ru.DmN.siberia.utils.klassOf
import ru.DmN.siberia.utils.vtype.*
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.TypeVariable
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

    private fun addType(klass: Klass): VirtualType =
        JavaVirtualTypeImpl(
            klass.name,
            klass.componentType?.let(::typeOf),
            klass.isInterface,
            Modifier.isFinal(klass.modifiers) || klass.isEnum
        ).apply {
            this@JRTP += this
            //
            klass.superclass?.let { parents.add(typeOf(it.name)) }
            Arrays.stream(klass.interfaces).map { typeOf(it.name) }.forEach(parents::add)
            //
            klass.typeParameters.forEach {
                val bound = it.bounds.lastOrNull()
                val generic = "${it.name}$$name"
                genericsAccept += generic
                genericsDefine[generic] = typeOf(if (bound is Klass) bound else Any::class.java)
            }
            klass.genericSuperclass.let {
                if (it is ParameterizedTypeImpl) {
                    it.actualTypeArguments.forEachIndexed { j, it1 ->
                        val type = klass.superclass
                        genericsMap["${type.typeParameters[j].name}$${type.name}"] = "${it1.typeName}$$name"
                    }
                }
            }
            klass.genericInterfaces.forEachIndexed { i, it0 ->
                if (it0 is ParameterizedTypeImpl) {
                    it0.actualTypeArguments.forEachIndexed { j, it1 ->
                        val type = klass.interfaces[i]
                        genericsMap["${type.typeParameters[j].name}$${type.name}"] = "${it1.typeName}$$name"
                    }
                }
            }
            parents.forEach { it0 ->
                it0.genericsMap.forEach { it1 ->
                    genericsMap[it1.value]?.let { genericsMap[it1.key] = it }
                }
            }
            //
            klass.declaredFields.forEach { fields += VirtualField.of(::typeOf, it) }
            klass.declaredConstructors.forEach { methods += methodOf(::typeOf, it) }
            scanTypeMethods(methods, klass)
        }

    private fun scanTypeMethods(list: MutableList<VirtualMethod>, klass: Klass) {
        klass.declaredMethods.forEach { list += methodOf(::typeOf, it) }
        if (klass.superclass == null)
            return
        scanTypeMethods(list, klass.superclass)
        klass.interfaces.forEach { scanTypeMethods(list, it) }
    }

    /**
     * Создаёт новый метод.
     * Использует typeOf метод для взятия новых типов по имени.
     */
    private fun methodOf(typeOf: (name: String) -> VirtualType, ctor: Constructor<*>): VirtualMethod =
        methodOf(typeOf(ctor.declaringClass.name), ctor)

    /**
     * Создаёт новый метод.
     * Использует typeOf метод для взятия новых типов по имени.
     */
    private fun methodOf(typeOf: (name: String) -> VirtualType, method: Method): VirtualMethod =
        methodOf(typeOf(method.declaringClass.name), method)

    /**
     * Создаёт новый метод.
     */
    private fun methodOf(declaringClass: VirtualType, method: Constructor<*>): VirtualMethod {
        val argsc = ArrayList<VirtualType>()
        val argsn = ArrayList<String>()
        val argsg = ArrayList<String?>()
        method.parameters.forEach {
            argsc += VirtualType.ofKlass(it.type)
            argsn += it.name
//            argsg += it.parameterizedType.let { if (it is TypeVariable<*>) "${it.name}$${decl}" else null } // А зачем?
        }
        return VirtualMethod.Impl(
            declaringClass,
            "<init>",
            VirtualType.VOID,
            null,
            argsc,
            argsn,
            argsg,
            MethodModifiers(
                varargs = method.isVarArgs,
                static = Modifier.isStatic(method.modifiers),
                abstract = method.declaringClass.isInterface,
                final = Modifier.isFinal(method.modifiers)
            ),
            null,
            null
        )
    }

    /**
     * Создаёт новый метод.
     */
    private fun methodOf(declaringClass: VirtualType, method: Method): VirtualMethod {
        val argsc = ArrayList<VirtualType>()
        val argsn = ArrayList<String>()
        val argsg = ArrayList<String?>()
        val generics = declaringClass.genericsDefine.toMutableMap()
        val decl = declaringClass.name
        method.parameters.forEach { it ->
            argsc += VirtualType.ofKlass(it.type)
            argsn += it.name
            argsg += it.parameterizedType.let { if (it is TypeVariable<*>) "${it.name}$$decl" else null }
        }
        // todo: add method only generics
        return PhtVirtualMethod.Impl(
            declaringClass,
            method.name,
            VirtualType.ofKlass(method.returnType),
            method.genericReturnType.let { if (it is TypeVariable<*>) "${it.name}$$decl" else null },
            argsc,
            argsn,
            argsg,
            MethodModifiers(
                varargs = method.isVarArgs,
                static = Modifier.isStatic(method.modifiers),
                abstract = Modifier.isAbstract(method.modifiers),
                final = Modifier.isFinal(method.modifiers)
            ),
            null,
            null,
            generics
        )
    }
}