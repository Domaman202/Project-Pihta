package ru.DmN.phtx.pls.processors

import com.kingmang.lazurite.parser.pars.FunctionAdder
import com.kingmang.lazurite.parser.pars.Lexer
import com.kingmang.lazurite.parser.pars.Parser
import ru.DmN.pht.std.utils.computeStringNodes
import ru.DmN.phtx.pls.processor.utils.convert
import ru.DmN.phtx.pls.processor.utils.nodePrognB
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line

object NRIncludeLzr : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VTDynamic

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val module = ctx.module
        val line = node.line
        val nodes = processor.computeStringNodes(node, ctx)
            .asSequence()
            .map { module.getModuleFile(it) }
            .map { Lexer.tokenize(it) }
            .map { Parser(it) }
            .map { it.parse().apply { if (it.parseErrors.hasErrors()) throw RuntimeException(it.parseErrors.toString()) } }
            .map { it.apply { accept(FunctionAdder()) } }
            .map { convert(line, it) }
            .toMutableList()
        return NRDefault.process(nodePrognB(line, nodes), processor, ctx, mode)
    }
}