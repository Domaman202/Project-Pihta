package ru.DmN.pht.base.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFunction
import ru.DmN.pht.base.utils.Generic
import ru.DmN.pht.base.utils.Generics
import ru.DmN.pht.std.utils.desc

open class ClassContext(val node: ClassNode, val clazz: VirtualType, val fields: MutableList<FieldContext> = ArrayList(), val methods: MutableList<MethodContext> = ArrayList()) {
    fun getSignature(node: NodeFunction) =
        StringBuilder().apply {
            append('(')
            node.args.list.forEach { append(getSignature(it.second)) }
            append(')').append(getSignature(node.rettype))
        }.toString()

    fun getSignature(type: String): String =
        type.let {
            if (it.endsWith('^'))
                "T${it.substring(0, type.length - 1)};"
            else it.desc
        }

    fun getSignature(type: VirtualType): String =
        if (type.generics.list.isEmpty())
            type.desc
        else {
            val sb = StringBuilder()
            type.generics.list.forEach { sb.append(it.extends) }
            "L${type.className}<$sb>;"
        }

    fun getDescriptor(compiler: Compiler, gctx: GlobalContext, node: NodeFunction) =
        StringBuilder().apply {
            append('(')
            node.args.list.forEach { append(getType(compiler, gctx, it.second).desc) }
            append(')').append(getType(compiler, gctx, node.rettype).desc)
        }.toString()

    fun getType(compiler: Compiler, gctx: GlobalContext, name: String): VirtualType {
        var generic: String? = null
        val type = gctx.getType(compiler, name.run {
            if (endsWith('^'))
                clazz.generics[substring(0, name.length - 1)]
            else indexOf('<').run {
                if (this == -1)
                    name
                else {
                    generic = name.substring(this + 1, name.length - 1).let { if (it.endsWith('^')) it else getType(compiler, gctx, it.substring(1)).name }
                    name.substring(0, this)
                }
            }
        })
        return if (generic == null)
            type
        else {
            val generics = generic!!.split('^').dropLast(1)
            type.with(Generics(ArrayList<Generic>().apply {
                generics.forEachIndexed { i, it ->
                    if (i > this.size - 1)
                        this += Generic("PHT$$i", getType(compiler, gctx, "$it^").desc)
                    this[i].extends = "T${it};"
                }
            }))
        }
    }
}