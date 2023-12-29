package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NREnum : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        //
        val type = VirtualType.VirtualTypeImpl(
            gctx.name(
                processor.computeString(
                    processor.process(
                        node.nodes[0],
                        ctx,
                        ValType.VALUE
                    )!!, ctx
                )
            )
        )
        processor.tp.types[type.name.hashCode()] = type
        //
        val info = node.info
        val new = NodeType(info.withType(NodeTypes.ENUM_), node.nodes.drop(2).toMutableList(), type)
        processor.stageManager.pushTask(ProcessingStage.TYPES_PREDEFINE) {
            type.parents =
                processor.computeList(processor.process(node.nodes[1], ctx, ValType.VALUE)!!, ctx)
                    .map { processor.computeType(it, ctx) }
                    .toMutableList()
            processor.stageManager.pushTask(ProcessingStage.TYPES_DEFINE) {
                val ectx = EnumContext(type)
                val context = ctx.with(ectx)
                processNodesList(new, processor, context, ValType.NO_VALUE)
                new.nodes += processor.process(
                    nodeStatic(
                        info,
                        mutableListOf(
                            nodeDefn(
                                node.info,
                                "<clinit>",
                                "void",
                                emptyList(),
                                mutableListOf(
                                    nodeProgn(
                                        info,
                                        ectx.enums.map {
                                            NodeFieldSet(
                                                info.withType(NodeTypes.FSET_),
                                                mutableListOf(nodeNew(info, type.name, it.args)),
                                                nodeValueClass(info, type.name),
                                                it.name,
                                                true
                                            )
                                        }.toMutableList()
                                    )
                                )
                            )
                        )
                    ), context, ValType.NO_VALUE
                )!!
            }
        }
        return new
    }
}