package ru.DmN.pht.processors

import ru.DmN.pht.ast.*
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.isMethod
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.FLD_
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.FieldModifiers
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl

object NRFld : INodeProcessor<NodeFieldA> {
    override fun process(node: NodeFieldA, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val gctx = ctx.global
        val clazz = ctx.clazz as PhtVirtualType.Impl
        val body = ArrayList<Node>()
        val fields = ArrayList<VirtualFieldImpl>()
        val info = node.info
        val final = node.final
        val static = node.static
        if (ctx.isMethod()) {
            processor.computeList(node.nodes[0], ctx).stream().map { processor.computeList(it, ctx) }.forEach {
                val name = processor.computeString(it[0], ctx)
                VirtualFieldImpl(
                    clazz,
                    name,
                    processor.calc(it[1], ctx)!!,
                    FieldModifiers(
                        isFinal = final,
                        isStatic = static,
                        isEnum = false
                    )
                ).process(node, info, clazz, fields, body)
                body.add(nodeInitFld(info, name, clazz, it[1]))
            }
        } else {
            processor.computeList(node.nodes[0], ctx)
                .stream()
                .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
                .forEach {
                    VirtualFieldImpl(
                        clazz,
                        it[0],
                        gctx.getType(it[1]),
                        FieldModifiers(
                            isFinal = final,
                            isStatic = static,
                            isEnum = false
                        )
                    ).process(node, info, clazz, fields, body)
                }
        }
        return processor.process(
            NodeMetaNodesList(
                info.withType(if (node.final) ANN_FINAL else PROGN_B),
                mutableListOf(
                    NodeMetaNodesList(
                        info.withType(if (node.static) ANN_STATIC else PROGN_B),
                        body.apply { this.add(0, NodeFieldB(info.withType(FLD_), fields)) }
                    )
                )
            ),
            ctx,
            valMode
        )!!
    }

    fun VirtualFieldImpl.process(node: NodeFieldA, info: INodeInfo, clazz: PhtVirtualType.Impl, fields: MutableList<VirtualFieldImpl>, body: MutableList<Node>) {
        fields += this
        clazz.fields += this
        if (!node.final) {
            body += nodeDefn(
                info,
                "set${name.let { it[0].uppercase() + it.substring(1) }}",
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
                        nodeGetVariable(info, "this", clazz),
                        name,
                        static = false,
                        native = true
                    )
                )
            )
        }
        body += nodeDefn(
            info,
            "get${name.let { it[0].uppercase() + it.substring(1) }}",
            type.name,
            emptyList(),
            listOf(
                if (node.static)
                    NodeFMGet(
                        info.withType(FGET_B),
                        mutableListOf(),
                        nodeValueClass(info, clazz.name),
                        name,
                        static = true,
                        native = true
                    )
                else NodeFMGet(
                    info.withType(FGET_B),
                    mutableListOf(),
                    nodeGetVariable(info, "this", clazz),
                    name,
                    static = false,
                    native = true
                )
            )
        )
    }
}