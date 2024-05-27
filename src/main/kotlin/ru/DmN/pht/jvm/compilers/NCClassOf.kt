package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Type
import ru.DmN.pht.ast.NodeClassOf
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCClassOf : IValueNodeCompiler<NodeClassOf> {
    override fun compileVal(node: NodeClassOf, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitLdcInsn(Type.getType(node.type.desc))
        return Variable.tmp(node, compiler.tp.typeOf("java.lang.Class"))
    }
}