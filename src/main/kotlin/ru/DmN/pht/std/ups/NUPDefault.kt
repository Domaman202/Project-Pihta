package ru.DmN.pht.std.ups

import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NUPDefault : INUPC<NodeNodesList, NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRDefault.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        NRDefault.process(node, processor, ctx, mode)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) =
        NCDefault.compile(node, compiler, ctx)

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        NCDefault.compileVal(node, compiler, ctx)
}