package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.compiler.java.utils.bytecodeCast
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.objectToPrimitive
import ru.DmN.pht.compiler.java.utils.primitiveToObject
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCAs : INodeCompiler<NodeIsAs> {
    override fun compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val value = compiler.compileVal(node.nodes[0], ctx)
            val of = value.type()
            val to = node.type
            if (of.isPrimitive) {
                if (to.isPrimitive) {
                    load(value, this)
                    bytecodeCast(of.name, to.name, this)
                } else primitiveToObject(value, this)
            } else {
                if (to.isPrimitive)
                    objectToPrimitive(value, this)
                else {
                    load(value, this)
                    visitTypeInsn(Opcodes.CHECKCAST, to.className)
                }
            }
        }
        return Variable.tmp(node, node.type)
    }
}