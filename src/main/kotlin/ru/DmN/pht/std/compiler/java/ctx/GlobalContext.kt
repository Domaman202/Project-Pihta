package ru.DmN.pht.std.compiler.java.ctx

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.isPrimitive

class GlobalContext(
    val namespace: String = "",
    val imports: MutableMap<String, String> = HashMap(),
    val extends: MutableList<Pair<String, MutableList<VirtualMethod>>> = ArrayList(),
    val macros: MutableList<MacroDefine> = ArrayList(),
) {
    fun with(namespace: String) =
        GlobalContext(
            namespace,
            imports.toMutableMap(),
            extends.toMutableList(),
            macros.toMutableList()
        )
    fun combineWith(context: GlobalContext) =
        GlobalContext(
            namespace,
            (imports + context.imports).toMutableMap(),
            (extends + context.extends).toMutableList(),
            (macros + context.macros).toMutableList()
        )

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

    fun getType(compiler: Compiler, name: String): VirtualType =
        getTypeOrThrow(compiler, imports[name] ?: name)

    fun getTypeOrNull(compiler: Compiler, name: String): VirtualType? =
        try {
            getType(compiler, name)
        } catch (_: ClassNotFoundException) {
            null
        }

    private fun getTypeOrThrow(compiler: Compiler, name: String): VirtualType {
        val classes = compiler.classes.map { it.clazz }
        classes.find { it.name == name }?.let { return it }
        return (if (name.contains('.') || name.isPrimitive()) name else name(name))
            .let { n -> classes.find { it.name == name(n) } ?: compiler.typeOf(n) }
    }
}