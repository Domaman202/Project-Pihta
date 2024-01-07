package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.ast.NodeType
import ru.DmN.pht.std.node.*
import ru.DmN.pht.std.processor.ctx.EnumContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeListOr
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
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val type = VirtualType.VirtualTypeImpl(
            gctx.name(
                processor.computeString(
                    processor.process(
                        node.nodes[offset],
                        ctx,
                        ValType.VALUE
                    )!!, ctx
                )
            )
        )
        processor.tp.types[type.name.hashCode()] = type
        //
        val info = node.info
        val new = NodeType(info.withType(NodeTypes.ENUM_), node.nodes.drop(2 + offset).toMutableList(), type)
        processor.stageManager.pushTask(ProcessingStage.TYPES_PREDEFINE) {
            type.parents =
                processor.computeList(processor.process(node.nodes[1 + offset], ctx, ValType.VALUE)!!, ctx)
                    .map { processor.computeType(it, ctx) }
                    .toMutableList()
            generics?.forEach {
                val generic = processor.computeList(it, ctx)
                type.generics += Pair(processor.computeString(generic[0], ctx), processor.computeType(generic[1], ctx))
            }
            processor.stageManager.pushTask(ProcessingStage.TYPES_DEFINE) {
                val ectx = EnumContext(type)
                val context = ctx.with(ectx)
                processNodesList(new, processor, context, ValType.NO_VALUE)
                processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
                    if (type.methods.find { it.name == "<clinit>" } == null) {
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
                                                ectx.enums.mapIndexed { i, it ->
                                                    NodeFSet(
                                                        info.withType(NodeTypes.FSET_),
                                                        mutableListOf(
                                                            nodeValueClass(info, type.name),
                                                            NRNew.process(
                                                                nodeNew(
                                                                    info,
                                                                    type.name,
                                                                    listOf(
                                                                        nodeValue(info, it.name),
                                                                        nodeValue(info, i)
                                                                    ) + it.args
                                                                ),
                                                                processor,
                                                                ctx,
                                                                ValType.VALUE
                                                            )
                                                        ),
                                                        type.fields.find { f -> f.name == it.name }!!
                                                    )
                                                }.toMutableList()
                                            )
                                        )
                                    )
                                )
                            ),
                            context,
                            ValType.NO_VALUE
                        )!!
                    }
                }
            }
        }
        return new
    }
}