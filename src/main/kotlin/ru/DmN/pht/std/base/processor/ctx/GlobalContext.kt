package ru.DmN.pht.std.base.processor.ctx

import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.compiler.java.utils.SubMap
import ru.DmN.pht.std.base.processor.utils.ICastable
import ru.DmN.pht.std.base.utils.lenArgs

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

    fun getMethodVariants(type: VirtualType, name: String, args: List<ICastable>, tp: TypesProvider): List<VirtualMethod> =
        getAllMethods(type)
            .asSequence()
            .filter { it.name == name }
            .filter { it.argsc.size == args.size || it.modifiers.varargs && args.size > it.argsc.size }
            .map { Pair(it, lenArgs(it.argsc.map { tp.typeOf(it.type) }, args)) }
            .filter { it.second > -1 }
            .sortedBy { it.second }
            .map { it.first }
            .toList()

    fun getAllMethods(type: VirtualType): List<VirtualMethod> =
        ArrayList<VirtualMethod>().apply {
            addAll(type.methods)
            addAll(getAllExtends(type))
        }

    fun getAllExtends(type: VirtualType): List<VirtualMethod> =
        ArrayList(getExtends(type)).apply { type.parents.forEach { addAll(getAllExtends(it)) } }

    fun getExtends(type: VirtualType): MutableList<VirtualMethod> {
        extends.find { it.first == type.name }?.let { return it.second }
        return ArrayList<VirtualMethod>().apply { extends.add(Pair(type.name, this)) }
    }

    fun getType(name: String, tp: TypesProvider): VirtualType = // todo: check name (проверка на содержание . в имени)
        imports[name]?.let { tp.typeOf(it) } ?: tp.typeOfOrNull(name) ?: tp.typeOf(name(name))
}