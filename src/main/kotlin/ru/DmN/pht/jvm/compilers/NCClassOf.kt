package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeClassOf
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.desc

object NCClassOf : INodeCompiler<NodeClassOf> {
    override fun compileVal(node: NodeClassOf, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitLdcInsn(Type.getType(node.name.desc))
        return Variable.tmp(node, compiler.tp.typeOf("java.lang.Class"))
    }
}