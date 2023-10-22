package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.Platform
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ProcessingStage
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.imports.StdImportsHelper
import ru.DmN.pht.std.ast.NodeImport
import ru.DmN.pht.std.imports.ast.IValueNode

object NUPImport : INodeUniversalProcessor<NodeNodesList, NodeImport> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val module = parser.nextOperation().text!!
        val context = ctx.subCtx()
        context.loadedModules.add(0, StdImportsHelper)
        return NPDefault.parse(parser, context) { it ->
            val map = HashMap<String, MutableList<Any?>>()
            it.forEach { map.getOrPut(it.token.text!!) { ArrayList() } += (it as IValueNode).value }
            NodeImport(operationToken, module, map)
        }
    }

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        TODO("Not yet implemented")
    }

    override fun process(node: NodeImport, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeImport? {
        val gctx = ctx.global

        processor.pushTask(ctx, ProcessingStage.TYPES_IMPORT) {
            node.data["type"]?.run {
                val imports = gctx.imports
                forEach {
                    it as String
                    imports[it.substring(it.lastIndexOf('.') + 1)] = it
                }
            }
        }

        processor.pushTask(ctx, ProcessingStage.EXTENDS_IMPORT) {
            node.data["extends"]?.forEach { it ->
                it as String
                gctx.getType(it, processor.tp).methods
                    .stream()
                    .filter { it.modifiers.extend }
                    .forEach { ctx.global.getExtends(it.extend!!) += it }
            }
        }

        return if (ctx.platform == Platform.JAVA)
            null
        else node
    }
}