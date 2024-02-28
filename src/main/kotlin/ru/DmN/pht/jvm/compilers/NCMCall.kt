package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.method
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCMCall : INodeCompiler<NodeMCall> {
    override fun compile(node: NodeMCall, compiler: Compiler, ctx: CompilationContext) {
        val pair = compileCallInsert(node, compiler, ctx)
        if (pair.second != VirtualType.VOID) {
            pair.first.visitInsn(Opcodes.POP)
        }
    }

    override fun compileVal(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Variable =
        Variable.tmp(node, compileCallInsert(node, compiler, ctx).second)

    private fun compileCallInsert(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Pair<MethodNode, VirtualType> {
        val mnode = ctx.method.node
        return if (node.inline == null) {
            node.method.run {
                if (!modifiers.static)
                    load(compiler.compileVal(node.instance, ctx), mnode)
                node.nodes.forEach { load(compiler.compileVal(it, ctx), mnode) }
                mnode.visitMethodInsn(
                    if (modifiers.static)
                        Opcodes.INVOKESTATIC
                    else if (declaringClass.isInterface)
                        Opcodes.INVOKEINTERFACE
                    else if (node.type == NodeMCall.Type.SUPER)
                        Opcodes.INVOKESPECIAL
                    else Opcodes.INVOKEVIRTUAL,
                    declaringClass.className,
                    name.normalizeName(),
                    desc,
                    declaringClass.isInterface
                )
                Pair(mnode, this.rettype)
            }
        } else {
            val result = compiler.compileVal(node.inline!!, ctx)
            load(result, mnode)
            Pair(mnode, result.type())
        }
    }
}