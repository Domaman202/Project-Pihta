package ru.DmN.pht.base

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.getRegex

class Processor(val getType: (name: String) -> VirtualType) {
    fun process(node: Node, ctx: ProcessingContext): Node =
        get(ctx, node).process(node, this, ctx)

    fun get(ctx: ProcessingContext, node: Node): INodeProcessor<Node> {
        val name = node.tkOperation.text!!
        val i = name.lastIndexOf('/')
        if (i < 1) {
            ctx.loadedModules.forEach { it -> it.processors.getRegex(name)?.let { return it as INodeProcessor<Node> } }
            throw RuntimeException()
        } else {
            val module = name.substring(0, i)
            return ctx.loadedModules.find { it.name == module }!!.compilers.getRegex(name.substring(i + 1)) as INodeProcessor<Node>
        }
    }
}