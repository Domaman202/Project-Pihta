package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.with
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.load

object NCDefn : INodeCompiler<NodeDefn> {
    override fun compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        val method = ctx.clazz.node.visitMethod(
            Opcodes.ACC_PUBLIC.let {
                if (node.static) it + Opcodes.ACC_STATIC
                else if (node.abstract) it + Opcodes.ACC_ABSTRACT
                else it
            },
            node.method.name,
            node.method.desc,
            null,
            null
        ) as MethodNode
        if (!node.abstract) {
            compiler.pushTask(ctx, CompilingStage.METHODS_BODY) {
                method.visit(node, node.method, compiler, ctx)
            }
        }
    }

    fun MethodNode.visit(node: NodeNodesList, method: VirtualMethod, compiler: Compiler, ctx: CompilationContext) {
        val start = Label()
        visitLabel(start)
        val body = BodyContext.of(start, method)
        val context = ctx.with(MethodContext(this, method)).with(body)
        if (method.rettype == VirtualType.VOID) {
            NCDefault.compile(node, compiler, context)
            visitInsn(Opcodes.RETURN)
        } else {
            load(NCDefault.compileVal(node, compiler, context), this)
            visitReturn(this, method.rettype)
        }
        val stop = Label()
        visitLabel(stop)
        body.stop = stop
        visit(body)
    }

    fun visitReturn(node: MethodVisitor, rettype: VirtualType) {
        node.visitInsn(
            when (rettype) {
                VirtualType.BOOLEAN,
                VirtualType.BYTE,
                VirtualType.SHORT,
                VirtualType.CHAR,
                VirtualType.INT     -> Opcodes.IRETURN
                VirtualType.LONG    -> Opcodes.LRETURN
                VirtualType.FLOAT   -> Opcodes.FRETURN
                VirtualType.DOUBLE  -> Opcodes.DRETURN
                else                -> Opcodes.ARETURN
            }
        )
    }

    private fun MethodNode.visit(body: BodyContext) {
        body.variables.let { if (it is SubList) it.list else it }.forEach {
            visitLocalVariable(
                it.name,
                it.type!!.desc,
                null,
                body.start,
                body.stop,
                it.id
            )
        }
        body.forEach { visit(it) }
    }
}