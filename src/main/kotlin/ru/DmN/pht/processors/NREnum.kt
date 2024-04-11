package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.ast.NodeType
import ru.DmN.pht.processor.ctx.EnumContext
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeListOr
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.*
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processor.utils.processNodesList
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushTask
import ru.DmN.siberia.utils.mapMutable

object NREnum : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val gctx = ctx.global
        //
        val generics = processor.computeListOr(node.nodes[0], ctx)
        val offset = if (generics == null) 0 else 1
        val type = PhtVirtualType.Impl(gctx.name(processor.computeString(node.nodes[offset], ctx,)))
        processor.tp.types[type.name.hashCode()] = type
        //
        val info = node.info
        val new = NodeType(info.withType(NodeTypes.ENUM_), node.nodes.drop(2 + offset).toMutableList(), type)
        processor.pushTask(TYPES_PREDEFINE, node) {
            type.parents =
                processor.computeList(processor.process(node.nodes[1 + offset], ctx, true)!!, ctx)
                    .mapMutable { processor.computeType(it, ctx) }
            generics?.forEach {
                val generic = processor.computeList(it, ctx)
                type.generics += Pair(processor.computeString(generic[0], ctx), processor.computeType(generic[1], ctx))
            }
            processor.pushTask(TYPES_DEFINE, node) {
                val ectx = EnumContext(type)
                val context = ctx.with(ectx)
                processNodesList(new, processor, context, false)
                processor.pushTask(METHODS_BODY, node) {
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
                                                            processor.process(
                                                                nodeNew(
                                                                    info,
                                                                    type.name,
                                                                    listOf(
                                                                        nodeValue(info, it.name),
                                                                        nodeValue(info, i)
                                                                    ) + it.args
                                                                ),
                                                                ctx,
                                                                true
                                                            )!!
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
                            false
                        )!!
                    }
                }
            }
        }
        return new
    }
}