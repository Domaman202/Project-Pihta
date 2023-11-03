package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.processor.utils.nodeProgn
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.ast.NodeMacroUnroll
import ru.DmN.pht.std.parser.macros
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
                    processor.computeList(NUPMacroArg.findMacroArgument(macro, node.uuids, it[1]), ctx).apply { maxIndex = min(maxIndex, size) },
                    it[0],
                    node.uuids.first()
                )
            }
        if (maxIndex != Int.MAX_VALUE) {
            for (i in 0 until maxIndex) {
                val args = HashMap<Pair<UUID, String>, Node>()
                names.forEach {
                    args[Pair(it.third, it.second)] = it.first[i]
                }
                nodes += NRDefault.process(
                    nodeProgn(node.token.line, node.nodes.drop(1).map { it.copy() }.toMutableList()),
                    processor,
                    ctx.with(NUPMacro.macroCtxOf(ctx, args)),
                    mode
                )
            }
        }
        return nodeProgn(node.token.line, nodes)
    }
}