package ru.DmN.pht.processor.ctx

import ru.DmN.pht.compiler.java.utils.MacroDefine
import ru.DmN.pht.processor.utils.ICastable
import ru.DmN.pht.processor.utils.getMethodVariants
import ru.DmN.pht.utils.vtype.arrayType
import ru.DmN.pht.utils.vtype.isArray
import ru.DmN.siberia.utils.SubList
import ru.DmN.siberia.utils.SubMap
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.vtype.TypesProvider
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class GlobalContext(
    val tp: TypesProvider,
    val namespace: String = "",
    val aliases: MutableMap<String, String> = HashMap(),
    val imports: MutableList<String> = ArrayList(),
    val methods: MutableMap<String, MutableList<VirtualMethod>> = HashMap(),
    val extensions: MutableList<Pair<String, MutableList<VirtualMethod>>> = ArrayList(),
    val macros: MutableList<MacroDefine> = ArrayList(),
    val macrosAliases: MutableMap<String, String> = HashMap(),
) {
    fun with(namespace: String) =
        GlobalContext(
            tp,
            namespace,
            SubMap(aliases),
            SubList(imports),
            SubMap(methods),
            SubList(extensions),
            SubList(macros),
            HashMap(macrosAliases)
        )

    fun combineWith(context: GlobalContext) =
        GlobalContext(
            tp,
            namespace,
            SubMap(aliases, context.aliases),
            SubList(imports, context.imports),
            SubMap(methods, context.methods),
            SubList(extensions, context.extensions),
            SubList(macros, context.macros),
            SubMap(macrosAliases, context.macrosAliases)
        )

    fun name(name: String): String =
        if (namespace.isEmpty()) name else "$namespace.$name"

    fun getMethodVariants(type: VirtualType, name: String, args: List<ICastable>): Sequence<Pair<VirtualMethod, Boolean>> =
        getMethodVariants(getAllMethods(type).filter { it.name == name }, args)

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

    fun getType(name: String): VirtualType {
        if (name.contains('.')) {
            try {
                return tp.typeOf(name)
            } catch (e: ClassNotFoundException) {
                throw e
            }
        }
        tp.typeOfOrNull(name)?.let { return it }
        aliases[name]?.let { return tp.typeOf(it) }
        for (i in imports.indices)
            tp.typeOfOrNull("${imports[i]}.$name")?.let { return it }
        throw MessageException(null, "Класс '$name' не найден!")
    }
}