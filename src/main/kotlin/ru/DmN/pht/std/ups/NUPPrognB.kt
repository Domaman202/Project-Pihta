package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.SimpleNR
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NUPPrognB : SimpleNR<NodeModifierNodesList>(), INUPC<NodeModifierNodesList, NodeModifierNodesList, NodeModifierNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) { NodeModifierNodesList(token, it) }

    override fun unparse(node: NodeModifierNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        super<SimpleNR>.calc(node, processor, ctx)

    override fun process(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeModifierNodesList =
        super<SimpleNR>.process(node, processor, ctx, mode)

    override fun compile(node: NodeModifierNodesList, compiler: Compiler, ctx: CompilationContext) =
        NCDefault.compile(node, compiler, ctx)

    override fun compileVal(node: NodeModifierNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        NCDefault.compileVal(node, compiler, ctx)
}