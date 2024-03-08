package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeIncDec
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCIncDec : ICppCompiler<NodeIncDec> {
    override fun StringBuilder.compileVal(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext): Variable {
        when (node.info.type) {
            INC_PRE_  -> append("++").append(node.name)
            INC_POST_ -> append(node.name).append("++")
            DEC_PRE_  -> append("--").append(node.name)
            DEC_POST_ -> append(node.name).append("--")
        }
        return Variable.tmp(node, node.type)
    }
}