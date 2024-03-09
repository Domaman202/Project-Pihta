package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCValue : ICppCompiler<NodeValue> {
    override fun StringBuilder.compileVal(node: NodeValue, compiler: Compiler, ctx: CompilationContext): Variable {
        when (node.vtype) {
            NIL    -> append("nullptr")
            CHAR   -> append('\'').append(node.value).append('\'')
            STRING -> append('"').append(node.value).append('"')
            else   -> append(node.value)
        }
        return Variable.tmp(
            node,
            when (node.vtype) {
                NIL     -> compiler.tp.typeOf("dmn.pht.object")
                BOOLEAN -> VirtualType.BOOLEAN
                CHAR    -> VirtualType.CHAR
                INT     -> VirtualType.INT
                LONG    -> VirtualType.LONG
                FLOAT   -> VirtualType.FLOAT
                DOUBLE  -> VirtualType.DOUBLE
                STRING  -> compiler.tp.typeOf("std.string")
                else    -> throw UnsupportedOperationException()
            }
        )
    }
}