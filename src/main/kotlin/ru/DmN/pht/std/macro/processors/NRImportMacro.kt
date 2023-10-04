package ru.DmN.pht.std.macro.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.macros
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString

object NRImportMacro : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        processor.pushTask(ctx, ProcessingStage.MACROS_IMPORT) {
            val gctx = ctx.global
            val mctx = processor.contexts.macros
            processor.computeList(node.nodes[0], ctx)
                .map { processor.computeString(it, ctx) }
                .forEach { it ->
                    val macro = it.substring(it.lastIndexOf('.') + 1)
                    val macros = mctx[it.substring(0, it.lastIndexOf('.'))]!!
                    if (macro == "*")
                        gctx.macros += macros
                    else gctx.macros += macros.find { it.name == macro }!!
                }
        }
        return null
    }
}