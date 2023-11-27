package ru.DmN.pht.std.processor.ctx

import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import ru.DmN.pht.std.processor.utils.ICastable
import ru.DmN.pht.std.utils.lenArgs

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

    fun getMethodVariants(type: VirtualType, name: String, args: List<ICastable>): List<VirtualMethod> =
        getAllMethods(type)
            .filter { it.name == name }
            .map { Pair(it, if (it.modifiers.extend) listOf(ICastable.of(it.extend!!)) + args else args) }
            .filter { it.first.argsc.size == it.second.size || it.first.modifiers.varargs }
            .map { Pair(it.first, lenArgs(it.first.argsc, it.second, it.first.modifiers.varargs)) }
            .filter { it.second > -1 }
            .sortedBy { it.second }
            .map { it.first }
            .toList()

    private fun getAllMethods(type: VirtualType): Sequence<VirtualMethod> {
        var seq = type.methods.asSequence() + getExtends(type).asSequence()
        type.parents.forEach { seq += getAllMethods(it) }
        return seq
    }

    fun getExtends(type: VirtualType): MutableList<VirtualMethod> {
        extends.find { it.first == type.name }?.let { return it.second }
        return ArrayList<VirtualMethod>().apply { extends.add(Pair(type.name, this)) }
    }

    fun getTypeName(name: String): String? =
        name.let { if (name.contains('.')) name else imports[name] }

    fun getType(name: String, tp: TypesProvider): VirtualType =
        getTypeName(name)
            ?.let { tp.typeOf(it) }
            ?: tp.typeOfOrNull(name)
            ?: tp.typeOf(name(name))
}