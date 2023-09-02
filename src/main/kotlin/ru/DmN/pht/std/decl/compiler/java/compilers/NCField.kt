package ru.DmN.pht.std.decl.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.decl.compiler.java.utils.clazz
import ru.DmN.pht.std.decl.compiler.java.utils.getType

object NCField : IStdNodeCompiler<NodeNodesList> { // todo: more fields
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val clazz = ctx.clazz
        val static = node.attributes.getOrDefault("static", false) as Boolean
        var access = Opcodes.ACC_PUBLIC
        if (node.attributes.getOrDefault("final", false) as Boolean)
            access += Opcodes.ACC_FINAL
        if (static)
            access += Opcodes.ACC_STATIC
        compiler.compute<List<Node>>(node.nodes.first(), ctx, ComputeType.NODE).forEach { it ->
            val pair = compiler.compute<List<Node>>(it, ctx, ComputeType.NODE)
                .map { compiler.compute<Node>(it, ctx, ComputeType.NODE) }
            clazz.fields += VirtualField(
                pair.first().getValueAsString(),
                clazz.getType(compiler, ctx.global, pair.last().getValueAsString()),
                static,
                false
            )
        }
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val text = annotation.tkOperation.text!!
        node.attributes[text.substring(text.lastIndexOf('@') + 1)] = true
    }
}