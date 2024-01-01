package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeFieldA
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.pht.std.ast.NodeFieldSet
import ru.DmN.pht.std.node.NodeParsedTypes.*
import ru.DmN.pht.std.node.NodeTypes.FLD_
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.NodeTypes.PROGN
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualType

object NRFld : INodeProcessor<NodeFieldA> {
    override fun process(node: NodeFieldA, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val gctx = ctx.global
        val clazz = ctx.clazz as VirtualType.VirtualTypeImpl
        val body = ArrayList<Node>()
        val fields = ArrayList<VirtualField.VirtualFieldImpl>()
        val info = node.info
        processor.computeList(node.nodes[0], ctx)
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach { it ->
                VirtualField.VirtualFieldImpl(
                    clazz,
                    it[0],
                    gctx.getType(it[1], processor.tp),
                    isStatic = node.static,
                    isEnum = false
                ).run {
                    fields += this
                    clazz.fields += this
                    body += nodeDefn(
                        info,
                        "set${name.let { it[0].toUpperCase() + it.substring(1) }}",
                        "void",
                        listOf(Pair(name, type.name)),
                        listOf(
                            if (node.static)
                                NodeFieldSet(
                                    info.withType(FSET_B),
                                    mutableListOf(nodeGetOrName(info, name)),
                                    nodeValueClass(info, clazz.name),
                                    name,
                                    static = true,
                                    native = true
                                )
                            else NodeFieldSet(
                                info.withType(FSET_B),
                                mutableListOf(nodeGetOrName(info, name)),
                                nodeGetOrName(info, "this"),
                                name,
                                static = false,
                                native = true
                            )
                        )
                    )
                    body += nodeDefn(
                        info,
                        "get${name.let { it[0].toUpperCase() + it.substring(1) }}",
                        type.name,
                        emptyList(),
                        listOf(
                            if (node.static)
                                NodeFMGet(
                                    info.withType(FGET_B),
                                    nodeValueClass(info, clazz.name),
                                    name,
                                    static = true,
                                    native = true
                                )
                            else NodeFMGet(
                                info.withType(FGET_B),
                                nodeGetOrName(info, "this"), name,
                                static = false,
                                native = true
                            )
                        )
                    )
                }
            }
        return processor.process(
            NodeNodesList(
                info.withType(if (node.static) ANN_STATIC else PROGN),
                body.apply { this += NodeFieldB(info.withType(FLD_), fields) }
            ),
            ctx,
            mode
        )!!
    }
}