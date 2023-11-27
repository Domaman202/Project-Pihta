package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeMacroUnroll
import ru.DmN.pht.std.parser.macros
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import java.util.*
import kotlin.math.min

object NUPMacroUnroll : INUP<NodeMacroUnroll, NodeMacroUnroll> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPDefault.parse(parser, ctx) {
            NodeMacroUnroll(token, it.toMutableList(), ctx.macros.reversed()).apply {
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