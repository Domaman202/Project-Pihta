package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.ast.NodeIsAs
import ru.DmN.pht.compiler.java.utils.bytecodeCast
import ru.DmN.pht.compiler.java.utils.objectToPrimitive
import ru.DmN.pht.compiler.java.utils.primitiveToObject
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.compilers.IValueNodeCompiler
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.utils.vtype.isArray
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCAs : IValueNodeCompiler<NodeIsAs> {
    override fun compileVal(node: NodeIsAs, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitCast(compiler.compileVal(node.nodes[0], ctx), node.type)
        return Variable.tmp(node, node.type)
    }

    fun MethodNode.visitCast(value: Variable, to: VirtualType) {
        val of = value.type
        if (of.isPrimitive) {
            if (to.isPrimitive) {
                ru.DmN.pht.compiler.java.utils.load(value, this)
                bytecodeCast(of.name, to.name, this)
            } else primitiveToObject(value, this)
        } else {
            if (to.isPrimitive)
                objectToPrimitive(value, to, this)
            else {
                ru.DmN.pht.compiler.java.utils.load(value, this)
                visitTypeInsn(Opcodes.CHECKCAST, if (to.isArray) to.desc else to.jvmName)
            }
        }
    }
}