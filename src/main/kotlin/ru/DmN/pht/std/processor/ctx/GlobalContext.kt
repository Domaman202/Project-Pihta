package ru.DmN.pht.std.processor.ctx

import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
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
            .asSequence()
            .filter { it.name == name }
            .map { Pair(it, if (it.modifiers.extend) listOf(ICastable.of(it.extend!!)) + args else args) }
            .filter { it.first.argsc.size == it.second.size || it.first.modifiers.varargs }
            .map { Pair(it.first, lenArgs(it.first.argsc, it.second, it.first.modifiers.varargs)) }
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
        ArrayList(getExtends(type)).apply {
            type.parents.forEach { addAll(getAllExtends(it)) }
            if (type.isArray) {
                type.componentType!!.parents.forEach {
                    addAll(getAllExtends(it.arrayType))
                }
            }
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