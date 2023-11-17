package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.*
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.module.StdModule
import ru.DmN.pht.std.processor.utils.exports
import ru.DmN.pht.std.processor.utils.isExports
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.text
import java.util.*

object NUPUseCtx : INodeUniversalProcessor<NodeUse, NodeParsedUse> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val names = ArrayList<String>()
        val exports = ArrayList<NodeNodesList>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        val context = ctx.subCtx()
        if (context.isExports())
            context.exports.push(exports)
        else context.exports = Stack<MutableList<NodeNodesList>>().apply { push(exports) }
        loadModules(names, parser, context)
        return NPDefault.parse(parser, context) { NodeParsedUse(operationToken, names, it, exports) }.apply { context.exports.pop() }
    }

    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        loadModules(node.names, unparser, ctx)
        unparser.out.apply {
            append('(').append(node.text).append(' ')
            node.names.forEachIndexed { i, it ->
                append(it)
                if (node.names.size + 1 < i) {
                    append(' ')
                }
            }
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }

    override fun process(node: NodeParsedUse, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val processed = ArrayList<Node>()
        processor.pushTask(ctx, ProcessingStage.MODULE_POST_INIT) {
            val context = ctx.subCtx()
            injectModules(node, processor, context, ValType.NO_VALUE, processed)
            node.exports.forEach { NRDefault.process(it, processor, ctx, ValType.NO_VALUE) }
            NRDefault.process(node, processor, context, mode)
        }
        return NodeProcessedUse(node.token, node.names, node.nodes, processed, node.exports)
    }

    fun loadModules(names: List<String>, parser: Parser, context: ParsingContext) {
        names.forEach { name ->
            val module = Module[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(Base, StdModule))
            (module ?: Module.getOrThrow(name)).inject(parser, context)
        }
    }

    fun loadModules(names: List<String>, unparser: Unparser, context: UnparsingContext) {
        names.forEach { name ->
            val module = Module[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(Base, StdModule))
            (module ?: Module.getOrThrow(name)).inject(unparser, context)
        }
    }

    fun injectModules(node: NodeUse, processor: Processor, ctx: ProcessingContext, mode: ValType, list: MutableList<Node>) =
        node.names.forEachIndexed { i, it -> Module.getOrThrow(it).inject(processor, ctx, if (i + 1 < node.names.size) ValType.NO_VALUE else mode)?.let { list += it } }
}