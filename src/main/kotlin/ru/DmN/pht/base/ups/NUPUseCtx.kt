package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeProcessedUse
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.module.StdModule

object NUPUseCtx : INodeUniversalProcessor<NodeUse, NodeUse> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val names = ArrayList<String>()
        var tk = parser.nextToken()!!
        while (tk.type == Token.Type.OPERATION) {
            names.add(tk.text!!)
            tk = parser.nextToken()!!
        }
        parser.tokens.push(tk)
        val context = ParsingContext(SubList(ctx.loadedModules), ctx.macros) // todo: substack
        loadModules(names, parser, context)
        return NPDefault.parse(parser, context) { NodeUse(operationToken, names, it) }
    }

    override fun unparse(node: NodeUse, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        loadModules(node.names, unparser, ctx)
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
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

    override fun process(node: NodeUse, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val processed = ArrayList<Node>()
        processor.pushTask(ctx, ProcessingStage.MODULE_POST_INIT) {
            val context = ctx.subCtx()
            injectModules(node, processor, context, mode, processed)
            NRDefault.process(node, processor, context, mode)
        }
        return NodeProcessedUse(node.tkOperation, node.names, node.nodes, processed)
    }

    fun loadModules(names: List<String>, parser: Parser, context: ParsingContext) {
        names.forEach { name ->
            val module = Module.MODULES[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(StdModule))
            (module ?: Module.MODULES[name]!!).inject(parser, context)
        }
    }

    fun loadModules(names: List<String>, unparser: Unparser, context: UnparsingContext) {
        names.forEach { name ->
            val module = Module.MODULES[name]
            if (module?.init != true)
                Parser(Module.getModuleFile(name)).parseNode(ParsingContext.of(StdModule))
            (module ?: Module.MODULES[name]!!).inject(unparser, context)
        }
    }

    fun injectModules(node: NodeUse, processor: Processor, ctx: ProcessingContext, mode: ValType, list: MutableList<Node>) =
        node.names.forEachIndexed { i, it -> Module.MODULES[it]!!.inject(processor, ctx, if (i + 1 < node.names.size) ValType.NO_VALUE else mode)?.let { list += it } }
}