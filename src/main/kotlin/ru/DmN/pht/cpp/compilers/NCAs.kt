package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.compiler.utils.nameType
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCAs : ICppCompiler<NodeIsAs> {
    override fun StringBuilder.compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        val flag = node.from.isPrimitive
        if (flag == node.type.isPrimitive) {
            append("((").append(node.type.nameType()).append(") ")
            compiler.compileVal(node.nodes[0], ctx).load(this)
            append(')')
        } else if (flag) {
            append("gc.alloc_ptr<dmn::pht::primitive<").append(node.from.nameType()).append(">>(")
            compiler.compileVal(node.nodes[0], ctx).load(this)
            append(')')
        } else {
            append("((dmn::pht::auto_ptr<dmn::pht::primitive<").append(node.type.nameType()).append(">>&) ")
            compiler.compileVal(node.nodes[0], ctx).load(this)
            append(")->").append(
                when (node.type) {
                    VirtualType.BOOLEAN -> "toBool"
                    VirtualType.BYTE    -> "toByte"
                    VirtualType.SHORT   -> "toShort"
                    VirtualType.CHAR    -> "toChar"
                    VirtualType.INT     -> "toInt"
                    VirtualType.LONG    -> "toLong"
                    VirtualType.FLOAT   -> "toFloat"
                    VirtualType.DOUBLE  -> "toDouble"
                    else -> "toPrimitive"
                }
            ).append("()")
        }
        return Variable.tmp(node, node.type)
    }
}