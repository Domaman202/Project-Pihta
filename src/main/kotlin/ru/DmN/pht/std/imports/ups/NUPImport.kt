package ru.DmN.pht.std.imports.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.nextOperation
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.imports.StdImportsHelper
import ru.DmN.pht.std.imports.ast.NodeImport
import ru.DmN.pht.std.imports.ast.IValueNode

object NUPImport : INodeUniversalProcessor<NodeNodesList, NodeImport> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val module = parser.nextOperation().text!!
        val context = ParsingContext(SubList(ctx.loadedModules), ctx.macros) // todo: substack
        context.loadedModules.add(0, StdImportsHelper)
        return NPDefault.parse(parser, context) { it ->
            val map = HashMap<String, MutableList<Any?>>()
            it.forEach { map.getOrPut(it.tkOperation.text!!) { ArrayList() } += (it as IValueNode).value }
            NodeImport(operationToken, module, map)
        }
    }

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        TODO("Not yet implemented")
    }

    override fun process(node: NodeImport, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeImport =
        node.apply {
            node.data["type"]?.run {
                val imports = ctx.global.imports
                forEach {
                    it as String
                    imports[it.substring(it.lastIndexOf('.') + 1)] = it
                }
            }
        }
}