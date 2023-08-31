package ru.DmN.pht.std.base.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.base.compiler.java.ctx.FieldContext
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.base.compiler.java.utils.compute
import ru.DmN.pht.std.base.compiler.java.utils.global

object NCField : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        compiler.pushTask(ctx, CompileStage.FIELDS_DEFINE) {
            val cctx = ctx.clazz
            val static = node.attributes.getOrDefault("static", false) as Boolean
            var access = Opcodes.ACC_PUBLIC
            if (node.attributes.getOrDefault("final", false) as Boolean)
                access += Opcodes.ACC_FINAL
            if (static)
                access += Opcodes.ACC_STATIC
            compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).forEach { it ->
                val pair = compiler.compute<List<Node>>(it, ctx, ComputeType.NODE)
                    .map { compiler.compute<Node>(it, ctx, ComputeType.NODE) }
                val type = cctx.getType(compiler, ctx.global, pair.last().getValueAsString())
                val name = pair.first().getValueAsString()
                val fnode = cctx.node.visitField(access, name, type.desc, type.signature, null) as FieldNode
                val field = VirtualField(name, type, static, false)
                cctx.clazz.fields += field
                cctx.fields += FieldContext(fnode, field)
            }
        }
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val text = annotation.tkOperation.text!!
        node.attributes[text.substring(text.lastIndexOf('@') + 1)] = true
    }
}