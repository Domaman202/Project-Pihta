package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.cpp.utils.vtype.VTString
import ru.DmN.pht.cpp.utils.vtype.VVTPointer
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCValue : ICppCompiler<NodeValue> {
    override fun StringBuilder.compileVal(node: NodeValue, compiler: Compiler, ctx: CompilationContext): Variable {
        when (node.vtype) {
            NIL    -> append("nullptr")
            CHAR   -> append('\'').append(node.value).append('\'')
            STRING -> append('"').append(node.value.replace("\n", "\\n\\\n")).append('"')
            else   -> append(node.value)
        }
        return Variable.tmp(
            node,
            when (node.vtype) {
                NIL     -> VVTPointer(PhtVirtualType.of(VirtualType.VOID))
                BOOLEAN -> VirtualType.BOOLEAN
                CHAR    -> VirtualType.CHAR
                INT     -> VirtualType.INT
                LONG    -> VirtualType.LONG
                FLOAT   -> VirtualType.FLOAT
                DOUBLE  -> VirtualType.DOUBLE
                STRING  -> VTString
                else    -> throw UnsupportedOperationException()
            }
        )
    }
}