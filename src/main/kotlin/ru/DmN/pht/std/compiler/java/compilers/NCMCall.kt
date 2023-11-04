package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeMCall

object NCMCall : INodeCompiler<NodeMCall> {
    override fun compile(node: NodeMCall, compiler: Compiler, ctx: CompilationContext) {
        val pair = compileCallInsert(node, compiler, ctx)
        if (pair.second.rettype != VirtualType.VOID) {
            pair.first.visitInsn(Opcodes.POP)
        }
    }

    override fun compileVal(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Variable =
        Variable.tmp(node, compileCallInsert(node, compiler, ctx).second.rettype)

    private fun compileCallInsert(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Pair<MethodNode, VirtualMethod> {
        node.method.run {
            val mnode = ctx.method.node
            if (!modifiers.static)
                load(compiler.compileVal(node.instance, ctx), mnode)
            node.nodes.forEach { load(compiler.compileVal(it, ctx), mnode) }
            mnode.visitMethodInsn(
                if (modifiers.static)
                    Opcodes.INVOKESTATIC
                else if (declaringClass!!.isInterface)
                    Opcodes.INVOKEINTERFACE
                else Opcodes.INVOKEVIRTUAL,
                declaringClass!!.className,
                name,
                desc,
                declaringClass!!.isInterface
            )
            return Pair(mnode, this)
        }
    }
}