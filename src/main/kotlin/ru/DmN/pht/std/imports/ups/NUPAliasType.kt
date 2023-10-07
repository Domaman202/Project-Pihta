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
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeStringNodes
import ru.DmN.pht.std.imports.ast.NodeAliasType

object NUPAliasType : INodeUniversalProcessor<NodeAliasType, NodeNodesList> { // todo: remake to multi alias
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeAliasType, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(node.tkOperation.text).append(' ').append(node.type).append(' ').append(node.new).append(')')
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAliasType {
        val nodes = processor.computeStringNodes(node, ctx)
        val type = nodes[0]
        val new = nodes[1]
        val gctx = ctx.global
        gctx.imports[new] = gctx.getTypeName(type) ?: type
        return NodeAliasType(node.tkOperation, type, new)
    }
}