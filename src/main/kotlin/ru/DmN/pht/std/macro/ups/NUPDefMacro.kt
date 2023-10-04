package ru.DmN.pht.std.macro.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.macros
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.macro.ast.NodeDefMacro
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