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
import ru.DmN.pht.std.base.processor.utils.macro
import ru.DmN.pht.std.base.processor.utils.nodePrognOf
import ru.DmN.pht.std.base.processor.utils.with
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.macro.ast.NodeMacroUnroll
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

object NUPMacroUnroll : INodeUniversalProcessor<NodeMacroUnroll, NodeMacroUnroll> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPDefault.parse(parser, ctx) {
            NodeMacroUnroll(operationToken, it.toMutableList(), ctx.macros.reversed()).apply {
                ctx.macros.pop()
            }
        }
    }

    override fun unparse(node: NodeMacroUnroll, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun process(node: NodeMacroUnroll, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = ArrayList<Node>()
        var maxIndex = Int.MAX_VALUE
        val names = ArrayList<Triple<List<Node>, String, UUID>>()
        val macro = ctx.macro
        processor.computeList(node.nodes[0], ctx)
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach {
                names += Triple(
                    processor.computeList(NUPMacroArg.findMacroArgument(macro, node.uuids, it[1]), ctx).apply { maxIndex = min(maxIndex, it.size) },
                    it[0],
                    node.uuids.first()
                )
            }
        if (maxIndex != Int.MAX_VALUE) {
            for (i in 0 until maxIndex + 1) {
                val args = HashMap<Pair<UUID, String>, Node>()
                names.forEach {
                    args[Pair(it.third, it.second)] = it.first[i]
                }
                nodes += NRDefault.process(
                    nodePrognOf(node.tkOperation.line, node.nodes.drop(1).map { it.copy() }.toMutableList()),
                    processor,
                    ctx.with(NUPMacro.macroCtxOf(ctx, args)),
                    mode
                )
            }
        }
        return nodePrognOf(node.tkOperation.line, nodes)
    }
}