package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.macros
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.ast.NodeDefMacro
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.siberia.ups.NUPDefault
import java.util.*
import kotlin.collections.ArrayList

object NUPDefMacro : INUP<NodeDefMacro, NodeDefMacro> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node {
        val uuid = UUID.randomUUID()
        ctx.macros.push(uuid)
        return NPDefault.parse(parser, ctx) {
            ctx.macros.pop()
            NodeDefMacro(token, it.toMutableList(), uuid)
        }
    }

    override fun unparse(node: NodeDefMacro, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUPDefault.unparse(node, unparser, ctx, indent)

    override fun process(node: NodeDefMacro, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        processor.stageManager.pushTask(ProcessingStage.MACROS_DEFINE) {
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