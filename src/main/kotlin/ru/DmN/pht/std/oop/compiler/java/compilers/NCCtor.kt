package ru.DmN.pht.std.oop.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.std.fp.ast.NodeDefn
import ru.DmN.pht.std.fp.compiler.java.compilers.NCDefn

object NCCtor : INodeCompiler<NodeDefn> {
    override fun compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        node.method.name = "<init>"
        NCDefn.compile(node, compiler, ctx)
    }
}