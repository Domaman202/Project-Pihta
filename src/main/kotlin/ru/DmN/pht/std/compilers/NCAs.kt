package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.isPrimitive
import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.pht.std.compiler.java.utils.*

object NCAs : INodeCompiler<NodeAs> {
    override fun compileVal(node: NodeAs, compiler: Compiler, ctx: CompilationContext): Variable {
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