package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.compiler.java.utils.bytecodeCast
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.objectToPrimitive
import ru.DmN.pht.compiler.java.utils.primitiveToObject
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.vtype.isArray
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable

object NCAs : IValueNodeCompiler<NodeIsAs> {
    override fun compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val value = compiler.compileVal(node.nodes[0], ctx)
            val of = value.type ?: ctx.global.getType("Any")
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
                    visitTypeInsn(Opcodes.CHECKCAST, if (to.isArray) to.desc else to.jvmName)
                }
            }
        }
        return Variable.tmp(node, node.type)
    }
}