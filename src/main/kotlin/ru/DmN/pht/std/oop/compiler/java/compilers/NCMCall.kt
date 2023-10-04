package ru.DmN.pht.std.oop.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.load
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.oop.ast.NodeMCall

object NCMCall : INodeCompiler<NodeMCall> {
    override fun compile(node: NodeMCall, compiler: Compiler, ctx: CompilationContext) {
        val mnode = ctx.method.node
        node.method.run {
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
            if (rettype.type != "void") {
                mnode.visitInsn(Opcodes.POP)
            }
        }
    }

    override fun compileVal(node: NodeMCall, compiler: Compiler, ctx: CompilationContext): Variable {
        val mnode = ctx.method.node
        node.method.run {
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
            return Variable.tmp(node, compiler.tp.typeOf(rettype.type))
        }
    }
} // todo: deduplicate