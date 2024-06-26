package ru.DmN.pht.processor.utils

import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.isLiteral
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.utils.vtype.VirtualMethod

enum class Static(val filter: (method: VirtualMethod) -> Boolean) {
    ANY({ true }),
    STATIC({ it.modifiers.static }),
    NO_STATIC({ !it.modifiers.static || it.modifiers.extension });

    companion object {
        fun ofInstanceNode(node: Node, processor: Processor, ctx: ProcessingContext): Static =
            if (node.isConstClass)
                if (processor.computeType(node, ctx).fields.any { it.name == "INSTANCE" })
                    ANY
                else STATIC
            else if (node.isLiteral && processor.computeString(node, ctx) == ".")
                ANY
            else NO_STATIC
    }
}