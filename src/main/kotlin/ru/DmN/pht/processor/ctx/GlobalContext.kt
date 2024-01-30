package ru.DmN.pht.processor.ctx

import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.utils.ICastable
import ru.DmN.pht.utils.lenArgs
import ru.DmN.siberia.utils.*

class GlobalContext(
    val namespace: String = "",
    val aliases: MutableMap<String, String> = HashMap(),
    val imports: MutableList<String> = ArrayList(),
    val methods: MutableMap<String, MutableList<VirtualMethod>> = HashMap(),
    val extensions: MutableList<Pair<String, MutableList<VirtualMethod>>> = ArrayList(),
    val macros: MutableList<MacroDefine> = ArrayList(),
) {
    fun with(namespace: String) =
        GlobalContext(
            namespace,
            SubMap(aliases),
            SubList(imports),
            SubMap(methods),
            SubList(extensions),
            SubList(macros)
        )

    fun combineWith(context: GlobalContext) =
        GlobalContext(
            namespace,
            SubMap(aliases, context.aliases),
            SubList(imports, context.imports),
            SubMap(methods, context.methods),
            SubList(extensions, context.extensions),
            SubList(macros, context.macros)
        )

    fun name(name: String): String =
        if (namespace.isEmpty()) name else "$namespace.$name"

    fun getMethodVariants(type: VirtualType, name: String, args: List<ICastable>): Sequence<Pair<VirtualMethod, Boolean>> =
        getMethodVariants(getAllMethods(type).filter { it.name == name }, args)

    fun getMethodVariants(variants: Sequence<VirtualMethod>, args: List<ICastable>): Sequence<Pair<VirtualMethod, Boolean>> =
        variants
            .map { Pair(it, if (it.modifiers.extension) listOf(ICastable.of(it.extension!!)) + args else args) }
            .filter { it.first.argsc.size == it.second.size || it.first.modifiers.varargs }
            .map { Pair(it.first, lenArgs(it.first.argsc, it.second, it.first.modifiers.varargs)) }
            .filter { it.second.first > -1 }
            .sortedBy { it.second.first }
            .map { Pair(it.first, it.second.second) }

    private fun getAllMethods(type: VirtualType): Sequence<VirtualMethod> =
        if (type.isArray) {
            var seq = getExtensions(type).asSequence()
            type.componentType!!.parents.forEach { seq += getAllMethods(it.arrayType) }
            seq
        } else {
            var seq = type.methods.asSequence() + getExtensions(type).asSequence()
            type.parents.forEach { seq += getAllMethods(it) }
            seq
        }

    fun getExtensions(type: VirtualType): MutableList<VirtualMethod> {
        extensions.find { it.first == type.name }?.let { return it.second }
        return ArrayList<VirtualMethod>().apply { extensions.add(Pair(type.name, this)) }
    }

    fun getTypeName(name: String): String? =
        name.let { if (name.contains('.')) name else aliases[name] }

    fun getType(name: String, tp: TypesProvider): VirtualType {
        if (name.contains('.'))
            return tp.typeOf(name)
        tp.typeOfOrNull(name)?.let { return it }
        aliases[name]?.let { return tp.typeOf(it) }
        for (i in imports.indices)
            tp.typeOfOrNull("${imports[i]}.$name")?.let { return it }
        throw ClassNotFoundException(name)
    }
}