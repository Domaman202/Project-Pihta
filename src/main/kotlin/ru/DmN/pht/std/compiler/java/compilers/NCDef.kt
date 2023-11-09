package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.compiler.java.utils.*
import ru.DmN.pht.std.processor.utils.isBody

object NCDef : INodeCompiler<NodeDef> {
    override fun compile(node: NodeDef, compiler: Compiler, ctx: CompilationContext) {
        if (ctx.isBody()) {
            val body = ctx.body
            ctx.method.node.run {
                node.variables.forEach { it ->
                    val variable = Variable(it.name, it.type, it.variable!!.id, false)
                    body.variables += variable
                    it.value?.let {
                        val value = compiler.compileVal(it, ctx)
                        load(value, this)
                        storeCast(variable, value.type(), this)
                    }
                }
            }
        } else {
            val clazz = ctx.clazz.node
            node.variables.forEach { it ->
                clazz.visitField(
                    Opcodes.ACC_PUBLIC
                        .let { if (node.static) it + Opcodes.ACC_STATIC else it }
                        .let { if (node.final) it + Opcodes.ACC_FINAL else it },
                    it.name,
                    it.type.desc,
                    null,
                    null
                )
            }
        }
    }
}