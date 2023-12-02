package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeWithGens
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.VariableWithGenerics
import ru.DmN.siberia.utils.VirtualType

object NUPWithGens : INUPC<NodeWithGens, NodeWithGens, NodeWithGens> {
    override fun calc(node: NodeWithGens, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)

    override fun unparse(node: NodeWithGens, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(NUDefaultX.text(node.token)).append('\n')
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            node.generics.forEach {
                append('\n').append("\t".repeat(indent + 1)).append('^').append(it.name)
            }
        }
    }

    override fun compileVal(node: NodeWithGens, compiler: Compiler, ctx: CompilationContext): VariableWithGenerics {
        val input = compiler.compileVal(node.nodes[0], ctx)
        return VariableWithGenerics(input.name, input.type, input.id, input.tmp, node.generics)
    }
}