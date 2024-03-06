package ru.DmN.pht.imports.parsers

import ru.DmN.pht.helper.ast.IValueNode
import ru.DmN.pht.helper.ast.NodeValueList
import ru.DmN.pht.parsers.NPValnB
import ru.DmN.pht.utils.node.NodeParsedTypes.VALN
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.utils.node.INodeInfo

object NPValueList : INodeParser {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPValnB.parse(parser, ctx) { it -> NodeValueList(INodeInfo.of(VALN), it.map { (it as IValueNode).value }) }
}