package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.cpp.utils.vtype.VTNativeString
import ru.DmN.pht.cpp.utils.vtype.normalizedName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCAs : ICppCompiler<NodeIsAs> {
    override fun StringBuilder.compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        val from = node.from
        val to = node.type

        if (from.isNative || to.isNative) {
            if (from == VTNativeString) {
                when (to) {
                    VTNativeString -> compiler.compileVal(node.nodes[0], ctx).load(this)
                    VirtualType.BOOLEAN -> {
                        compiler.compileVal(node.nodes[0], ctx).load(this)
                        append(" == \"true\"")
                    }
                    else -> {
                        append("std::sto").append(
                            when (to) {
                                VirtualType.BYTE,
                                VirtualType.SHORT,
                                VirtualType.CHAR,
                                VirtualType.INT     -> 'i'
                                VirtualType.LONG    -> 'l'
                                VirtualType.FLOAT   -> 'f'
                                VirtualType.DOUBLE  -> 'd'
                                else                -> throw RuntimeException()
                            }
                        ).append('(')
                        compiler.compileVal(node.nodes[0], ctx).load(this)
                        append(')')
                    }
                }
            } else if (to == VTNativeString) {
                compiler.compileVal(node.nodes[0], ctx).load(this)
                append("->toString()")
            } else if (from.isPrimitive) {
                if (to.isPrimitive) {
                    append('(').append(to.normalizedName).append(')')
                    compiler.compileVal(node.nodes[0], ctx).load(this)
                } else {
                    append("gc.alloc_ptr<dmn::pht::primitive<").append(from.normalizedName).append(">>(")
                    compiler.compileVal(node.nodes[0], ctx).load(this)
                    append(')')
                }
            } else if (to.isPrimitive) {
                compiler.compileVal(node.nodes[0], ctx).load(this)
                append(".cast<dmn::pht::primitive<").append(to.normalizedName).append(">>()->").append(
                    when (to) {
                        VirtualType.BOOLEAN -> "toBool"
                        VirtualType.BYTE    -> "toByte"
                        VirtualType.SHORT   -> "toShort"
                        VirtualType.CHAR    -> "toChar"
                        VirtualType.INT     -> "toInt"
                        VirtualType.LONG    -> "toLong"
                        VirtualType.FLOAT   -> "toFloat"
                        VirtualType.DOUBLE  -> "toDouble"
                        else                -> "toPrimitive"
                    }
                ).append("()")
            } else throw UnsupportedOperationException()
        } else {
            compiler.compileVal(node.nodes[0], ctx).load(this)
            append(".cast<").append(to.normalizedName).append(">()")
        }

        return Variable.tmp(node, to)
    }
}