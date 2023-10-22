package ru.DmN.pht.std.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.compiler.java.utils.storeCast

object NCDef : INodeCompiler<NodeDef> {
    override fun compile(node: NodeDef, compiler: Compiler, ctx: CompilationContext) {
        val body = ctx.body
        ctx.method.node.run {
            node.variables.forEach { it ->
                val variable = Variable(it.name, it.type, it.id, false)
                body.variables += variable
                it.value?.let {
                    val value = compiler.compileVal(it, ctx)
                    load(value, this)
                    storeCast(variable, value.type(), this)
                }
            }
        }
    }
}