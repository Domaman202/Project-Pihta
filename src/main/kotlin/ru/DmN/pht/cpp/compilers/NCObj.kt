package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.compiler.cpp.compilers.NCCls.compileHead
import ru.DmN.pht.compiler.cpp.compilers.NCCls.compileTail
import ru.DmN.pht.cpp.compiler.utils.name
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCObj : ICppCompiler<NodeType> {
    override fun StringBuilder.compile(node: NodeType, compiler: Compiler, ctx: CompilationContext) {
        compileHead(node, compiler, ctx)
        val name = node.type.name()
        append("public:\nstatic $name* INSTANCE() {\nstatic auto INSTANCE = gc.alloc_ptr<$name>();\nreturn INSTANCE.get();\n}\n};\n\n")
        compileTail(node, compiler, ctx)
    }

    override fun StringBuilder.compileVal(node: NodeType, compiler: Compiler, ctx: CompilationContext): Variable {
        compile(node, compiler, ctx)
        append(node.type.name()).append("::INSTANCE")
        return Variable.tmp(node, node.type)
    }
}