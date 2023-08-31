package ru.DmN.pht.std.base.compiler.java.ctx

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.isPrimitive
import ru.DmN.pht.std.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.compiler.java.utils.SubMap

class GlobalContext(
    val namespace: String = "",
    val imports: MutableMap<String, String> = HashMap(),
    val extends: MutableList<Pair<String, MutableList<VirtualMethod>>> = ArrayList(),
    val macros: MutableList<MacroDefine> = ArrayList(),
) {
    fun with(namespace: String) =
        GlobalContext(
            namespace,
            SubMap(imports),
            SubList(extends),
            SubList(macros)
        )

    fun combineWith(context: GlobalContext) =
        GlobalContext(
            namespace,
            SubMap(imports, context.imports),
            SubList(extends, context.extends),
            SubList(macros, context.macros)
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

    fun getType(compiler: Compiler, name: String) =
        getTypeOrThrow(compiler, imports[name] ?: name)

    private fun getTypeOrThrow(compiler: Compiler, name: String): VirtualType {
        val classes = compiler.classes.map { it.first }
        classes.find { it.name == name }?.let { return it }
        return (if (name.contains('.') || name.startsWith('[') || name.isPrimitive()) name else name(name))
            .let { n -> classes.find { it.name == name(n) } ?: compiler.typeOf(n) }
    }
}