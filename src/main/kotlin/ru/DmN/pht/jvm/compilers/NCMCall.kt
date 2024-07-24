package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.SUPER
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualMethod
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

    private fun compileCallInsert(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Pair<MethodNode, VirtualType> =
        node.method.run {
            if (node.special == null) {
                compileCallInsert(node, node.type, node.instance, node.method, compiler, ctx)
            } else if (modifiers.generator) {
                val special = node.special as NodeDefn
                val method = special.method
                if (ctx.clazz.node.methods.none { it.name == method.name && it.desc == method.desc }) {
                    node.method.modifiers.static = true
                    NCDefn.compileAsm(special, compiler, ctx)
                }
                compileCallInsert(node, NodeMCall.Type.UNKNOWN, node.instance, method, compiler, ctx)
            } else {
                val mnode = ctx.method.node
                val result = compiler.compileVal(node.special!!, ctx)
                load(result, mnode)
                Pair(mnode, result.type)
            }
        }

    private fun compileCallInsert(node: INodesList, type: NodeMCall.Type, instance: Node, method: VirtualMethod, compiler: Compiler, ctx: CompilationContext): Pair<MethodNode, VirtualType> =
        method.run {
            val mnode = ctx.method.node
            if (!modifiers.static || modifiers.generator && modifiers.extension)
                load(compiler.compileVal(instance, ctx), mnode)
            node.nodes.forEach { load(compiler.compileVal(it, ctx), mnode) }
            mnode.visitMethodInsn(
                if (modifiers.static)
                    Opcodes.INVOKESTATIC
                else if (declaringClass.isInterface)
                    Opcodes.INVOKEINTERFACE
                else if (type == SUPER)
                    Opcodes.INVOKESPECIAL
                else Opcodes.INVOKEVIRTUAL,
                declaringClass.jvmName,
                name.normalizeName(),
                desc,
                declaringClass.isInterface
            )
            Pair(mnode, rettype)
        }
}