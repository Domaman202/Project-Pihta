package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.method

object NCIncDec : INodeCompiler<NodeIncDec> {
    override fun compile(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            visitIincInsn(ctx.body[node.name]!!.id, when (node.type) {
                NodeIncDec.Type.INC -> 1
                NodeIncDec.Type.DEC -> -1
            })
        }
    }

    override fun compileVal(node: NodeIncDec, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            val id = ctx.body[node.name]!!.id
            visitIincInsn(id, when (node.type) {
                NodeIncDec.Type.INC -> 1
                NodeIncDec.Type.DEC -> -1
            })
            visitVarInsn(Opcodes.ILOAD, id)
        }
        return Variable.tmp(node, VirtualType.INT)
    }
}