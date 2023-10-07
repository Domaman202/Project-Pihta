package ru.DmN.pht.std.macro.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.base.processor.ctx.MacroContext
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.processor.utils.*
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.macro.ast.NodeMacro
import java.util.UUID

object NUPMacro : INodeUniversalProcessor<NodeMacro, NodeMacro> { // todo: calc
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val name = parser.nextOperation().text!!
        return NPDefault.parse(parser, ctx) { NodeMacro(operationToken, it, name) }
    }

    override fun unparse(node: NodeMacro, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append(node.name)
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun calc(node: NodeMacro, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val result = macroCalc(node, ctx)
        return NRDefault.calc(result.first, processor, result.second)
    }

    override fun process(node: NodeMacro, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val result = macroCalc(node, ctx)
        return NRDefault.process(result.first, processor, result.second, mode)
    }

    private fun macroCalc(node: NodeMacro, ctx: ProcessingContext): Pair<NodeNodesList, ProcessingContext> {
        val gctx = ctx.global
        //
        val macro = gctx.macros.find { it.name == node.name }!!
        val args = HashMap<Pair<UUID, String>, Node>()
        //
        if (macro.args.size == node.nodes.size)
            macro.args.forEachIndexed { i, it -> args[Pair(macro.uuid, it)] = node.nodes[i] }
        else if (macro.args.isNotEmpty()) {
            macro.args.dropLast(1).forEachIndexed { i, it -> args[Pair(macro.uuid, it)] = node.nodes[i] }
            args[Pair(macro.uuid, macro.args.last())] = NodeNodesList(
                Token(node.tkOperation.line, Token.Type.OPERATION, "valn"),
                node.nodes.drop(args.size - 1).toMutableList()
            )
        }
        //
        return Pair(
            nodePrognOf(node.tkOperation.line, macro.body.toMutableList()),
            ctx.with(macro.ctx.combineWith(gctx)).with(macroCtxOf(ctx, args))
        )
    }

    fun macroCtxOf(ctx: ProcessingContext, args: MutableMap<Pair<UUID, String>, Node>): MacroContext {
        return if (ctx.isMacro())
            MacroContext(SubMap(ctx.macro.args, args))
        else MacroContext(args)
    }
}