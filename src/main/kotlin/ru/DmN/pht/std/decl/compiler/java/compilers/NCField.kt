package ru.DmN.pht.std.decl.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.compute
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.decl.compiler.java.utils.clazz
import ru.DmN.pht.std.decl.compiler.java.utils.getType

object NCField : IStdNodeCompiler<NodeNodesList> { // todo: more fields
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val clazz = ctx.clazz
        val static = node.attributes.getOrDefault("static", false) as Boolean
        node.nodes.map { it -> compiler.compute<List<Node>>(it, ctx, ComputeType.NAME).map { compiler.computeName(it, ctx) } }
            .forEach { clazz.fields += VirtualField(it.first(), clazz.getType(compiler, ctx.global, it.last()), static, false) }
        return null
    }

    override fun applyAnnotation(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, annotation: Node) {
        val text = annotation.tkOperation.text!!
        node.attributes[text.substring(text.lastIndexOf('@') + 1)] = true
    }
}