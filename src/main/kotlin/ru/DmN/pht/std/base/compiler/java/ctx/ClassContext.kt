package ru.DmN.pht.std.base.compiler.java.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.utils.Generic
import ru.DmN.pht.base.utils.Generics
import ru.DmN.pht.base.utils.TypesProvider
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.ctx.GlobalContext

open class ClassContext(val node: ClassNode, val clazz: VirtualType) {
    fun getType(gctx: GlobalContext, tp: TypesProvider, name: String): VirtualType {
        var generic: String? = null
        val type = gctx.getType(name.run {
            if (endsWith('^'))
                clazz.generics[substring(0, name.length - 1)]
            else indexOf('<').run {
                if (this == -1)
                    name
                else {
                    generic = name.substring(this + 1, name.length - 1).let { if (it.endsWith('^')) it else getType(gctx, tp, it.substring(1)).name }
                    name.substring(0, this)
                }
            }
        }, tp)
        return if (generic == null)
            type
        else {
            val generics = generic!!.split('^').dropLast(1)
            type.with(Generics(ArrayList<Generic>().apply {
                generics.forEachIndexed { i, it ->
                    if (i > this.size - 1)
                        this += Generic("PHT$$i", getType(gctx, tp, "$it^").desc)
                    this[i].extends = "T${it};"
                }
            }))
        }
    }
}