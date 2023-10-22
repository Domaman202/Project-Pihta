package ru.DmN.pht.std.compilers

import org.objectweb.asm.Opcodes
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
        node.method.run {
            val mnode = ctx.method.node
            if (node.type != NodeMCall.Type.STATIC)
                load(compiler.compileVal(node.instance, ctx), mnode)
            node.nodes.forEach { load(compiler.compileVal(it, ctx), mnode) }
            mnode.visitMethodInsn(
                when (node.type) {
                    NodeMCall.Type.VIRTUAL,
                    NodeMCall.Type.THIS -> {
                        if (declaringClass!!.isInterface)
                            Opcodes.INVOKEINTERFACE
                        else Opcodes.INVOKEVIRTUAL
                    }

                    NodeMCall.Type.SUPER -> Opcodes.INVOKESPECIAL
                    NodeMCall.Type.EXTEND,
                    NodeMCall.Type.STATIC -> Opcodes.INVOKESTATIC

                    else -> throw RuntimeException()
                },
                declaringClass!!.className,
                name,
                desc,
                declaringClass!!.isInterface
            )
            if (rettype != VirtualType.VOID) {
                mnode.visitInsn(Opcodes.POP)
            }
        }
    }

    override fun compileVal(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Variable {
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
            return Variable.tmp(node, rettype)
        }
    }
} // todo: deduplicate