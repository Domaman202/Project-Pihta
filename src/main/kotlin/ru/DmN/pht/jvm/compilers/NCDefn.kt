package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode
import ru.DmN.pht.ast.NodeDefn
import ru.DmN.pht.compiler.java.ctx.BodyContext
import ru.DmN.pht.compiler.java.ctx.MethodContext
import ru.DmN.pht.compiler.java.utils.classes
import ru.DmN.pht.compiler.java.utils.clazz
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.with
import ru.DmN.pht.jvm.compilers.IStdNodeCompiler
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.utils.SubList
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NCDefn : IStdNodeCompiler<NodeDefn, MethodNode> {
    override fun compile(node: NodeDefn, compiler: Compiler, ctx: CompilationContext) {
        val cnode = ctx.clazz.node
        //
        val method = node.method
        val name = method.name.normalizeName()
        val mnode = cnode.visitMethod(
            Opcodes.ACC_PUBLIC.let {
                if (node.static) it + Opcodes.ACC_STATIC
                else if (node.abstract) it + Opcodes.ACC_ABSTRACT
                else if (node.open) it
                else it + Opcodes.ACC_FINAL
            }.let {
                if (node.sync) it + Opcodes.ACC_SYNCHRONIZED
                else it
            },
            name,
            method.desc,
            method.signature,
            null
        ) as MethodNode
        //
        if (!node.abstract) {
            compiler.stageManager.pushTask(CompilingStage.METHODS_BODY) {
                mnode.visit(node, method, compiler, ctx)
                if (method.argsg.asSequence().filterNotNull().any()) {
                    findOtherMethods(method, name).forEach { it ->
                        cnode.visitMethod(
                            Opcodes.ACC_PUBLIC + Opcodes.ACC_BRIDGE + Opcodes.ACC_SYNTHETIC,
                            it.name.normalizeName(),
                            it.desc,
                            it.signature,
                            null
                        ).run {
                            var id = 1
                            it.argsc.forEachIndexed { i, it ->
                                visitVarInsn(Opcodes.ALOAD, 0)
                                load(it.name, id++, this)
                                val target = method.argsc[i]
                                if (!it.isPrimitive && !target.isPrimitive)
                                    visitTypeInsn(Opcodes.CHECKCAST, target.className)
                                if (it == VirtualType.FLOAT || it == VirtualType.DOUBLE) {
                                    id++
                                }
                            }
                            visitMethodInsn(Opcodes.INVOKEVIRTUAL, cnode.name, it.name, method.desc, false)
                            visitReturn(this, it.rettype)
                        }
                    }
                }
            }
        }
    }

    override fun getAsm(node: NodeDefn, compiler: Compiler, ctx: CompilationContext): MethodNode = // todo: тестовый код, потом поправить надо
        compiler.contexts.classes[node.method.declaringClass!!.name]!!.methods.find { it.name == node.method.name && it.desc == node.method.desc }!!

    private fun findOtherMethods(method: VirtualMethod, name: String): Sequence<VirtualMethod> {
        var seq = emptySequence<VirtualMethod>()
        method.declaringClass!!.parents.forEach { seq += findMethods(it, name) }
        return seq
            .filter { !it.modifiers.static }
            .filter { it.name == name }
            .filter { m -> m.argsc.mapIndexed { i, type -> method.argsc[i].isAssignableFrom(type) }.all { it } }
    }

    private fun findMethods(type: VirtualType, name: String): Sequence<VirtualMethod> {
        var seq = type.methods.asSequence()
        type.parents.forEach { seq += findMethods(it, name) }
        return seq
    }

    fun MethodNode.visit(node: INodesList, method: VirtualMethod, compiler: Compiler, ctx: CompilationContext) {
        val start = Label()
        visitLabel(start)
        val body = BodyContext.of(start, method)
        val context = ctx.with(MethodContext(this, method)).with { visitReturn(this, method.rettype) }.with(body)
        if (method.rettype == VirtualType.VOID) {
            NCDefault.compile(node as Node, compiler, context)
            visitInsn(Opcodes.RETURN)
        } else {
            val variable = NCDefault.compileVal(node as Node, compiler, context)
            if (variable.type == VirtualType.VOID)
                visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
            else load(variable, this)
            visitReturn(this, method.rettype)
        }
        val stop = Label()
        visitLabel(stop)
        body.stop = stop
        visit(body)
    }

    private fun visitReturn(node: MethodVisitor, rettype: VirtualType) {
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