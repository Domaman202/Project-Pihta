package ru.DmN.pht.std.out.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.fp.ast.NodeValue
import ru.DmN.pht.std.oop.ups.NUPMCallA

object NRPrint : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList =
        NUPMCallA.process(
            NodeNodesList(
                Token.operation(node.tkOperation.line, "mcall"),
                mutableListOf<Node>(
                    NodeValue.of(
                        node.tkOperation.line,
                        NodeValue.Type.CLASS,
                        "ru.DmN.pht.std.out.StdOut"
                    ),
                    NodeValue.of(
                        node.tkOperation.line,
                        NodeValue.Type.STRING,
                        node.tkOperation.text!!
                    )
                ).apply { addAll(node.nodes) }
            ),
            processor,
            ctx,
            mode
        )
}