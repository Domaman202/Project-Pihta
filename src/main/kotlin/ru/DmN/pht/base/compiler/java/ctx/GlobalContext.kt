package ru.DmN.pht.base.compiler.java.ctx

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType

class GlobalContext(val namespace: String = "", val extends: MutableList<Pair<String, MutableList<VirtualMethod>>> = ArrayList()) {
    fun with(namespace: String) =
        GlobalContext(namespace, extends)

    fun name(name: String): String =
        if (namespace.isEmpty()) name else "$namespace.$name"

    fun getAllExtends(type: VirtualType): List<VirtualMethod> {
        val list = ArrayList(getExtends(type))
        type.parents.forEach { list += getAllExtends(it) }
        return list
    }

    fun getExtends(type: VirtualType): MutableList<VirtualMethod> {
        extends.find { it.first == type.name }?.let { return it.second }
        return ArrayList<VirtualMethod>().apply { extends.add(Pair(type.name, this)) }
    }

    fun getType(compiler: Compiler, name: String): VirtualType {
        val classes = compiler.classes.map { it.clazz }
        return (classes.find { it.name == name }
            ?: (if (name.contains('.')) name else name(name)).let { n -> classes.find { it.name == n } }
            ?: compiler.typeOf(name))
    }

    fun getTypeOrNull(compiler: Compiler, name: String): VirtualType? =
        try {
            getType(compiler, name)
        } catch (_: ClassNotFoundException) {
            null
        }
}