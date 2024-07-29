package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeDef
import ru.DmN.pht.compiler.java.utils.load
import ru.DmN.pht.compiler.java.utils.storeCast
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler

object NCDef : INodeCompiler<NodeDef> {
    override fun compile(node: NodeDef, compiler: Compiler, ctx: CompilationContext) {
        if (node.isVariable) {
            val body = ctx.body
            ctx.method.node.run {
                node.variables.forEach { it ->
                    val variable = body.add(it.name.normalizeName(), it.type)
                    it.value?.let {
                        val value = compiler.compileVal(it, ctx)
                        load(value, this)
                        storeCast(variable, value.type, this)
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