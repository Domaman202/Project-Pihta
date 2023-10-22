package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.macros
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.ast.NodeDefMacro
import ru.DmN.pht.std.parser.macros
import java.util.*
import kotlin.collections.ArrayList

object NUPDefMacro : INodeUniversalProcessor<NodeDefMacro, NodeDefMacro> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPDefault.parse(parser, ctx) {
            ctx.macros.pop()
            NodeDefMacro(operationToken, it.toMutableList(), uuid)
        }
    }

    override fun unparse(node: NodeDefMacro, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun process(node: NodeDefMacro, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        processor.pushTask(ctx, ProcessingStage.MACROS_DEFINE) {
            val name = processor.computeString(node.nodes[0], ctx)
            val args = processor.computeList(node.nodes[1], ctx).map { processor.computeString(it, ctx) }
            val nodes = node.nodes.drop(2).toMutableList()
            val def = MacroDefine(node.uuid, name, args, nodes, ctx.global)
            ctx.global.macros += def
            processor.contexts.macros.getOrPut(ctx.global.namespace) { ArrayList() } += def
        }
        return null
    }
}