package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.utils.node.NodeTypes.PRINTLN_
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext

object NCPrint : ICppNRCompiler<NodeNodesList> {
    override fun StringBuilder.compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        append("std::cout")
        node.nodes.forEach {
            append("<<")
            val i = length
            compiler.compileVal(it, ctx).run {
                load(this@compile)
                if (type?.isPrimitive == false) {
                    replace(i, i, "dmn::pht::utils::toString(")
                    append(')')
                }
            }
        }
        if (node.info.type == PRINTLN_) {
            append("<<std::endl")
        }
    }
}