package ru.DmN.pht.std.fp.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompilingStage
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.ctx.BodyContext
import ru.DmN.pht.std.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.base.compiler.java.utils.with
import ru.DmN.pht.std.fp.ast.NodeDefn

object NCDefn : INodeCompiler<NodeDefn> {
    override fun compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) { // todo: abstract
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
        compiler.pushTask(ctx, CompilingStage.METHODS_BODY) {
            val start = Label()
            method.visitLabel(start)
            val body = BodyContext.of(null, start)
            if (!node.method.modifiers.static)
                body.variables += Variable("this", node.method.declaringClass, 0, false)
            val context = ctx.with(MethodContext(method, node.method)).with(body)
            if (node.method.rettype.type == "void")
                NCDefault.compile(node, compiler, context)
            else NCDefault.compileVal(node, compiler, context)
            // todo: ret-val
            method.visitInsn(Opcodes.RETURN)
            val stop = Label()
            method.visitLabel(stop)
            body.stop = stop
            body.visit(method)
        }
    }

    private fun BodyContext.visit(node: MethodNode) {
        this.variables.forEach {
            node.visitLocalVariable(
                it.name,
                it.type!!.desc,
                null,
                this.start,
                this.stop,
                it.id
            )
        }
        this.forEach { it.visit(node) }
    }
}