package ru.DmN.pht.std.node

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.std.ast.*
import ru.DmN.pht.std.ast.NodeValue.Type.NIL
import ru.DmN.pht.std.node.NodeParsedTypes.*
import ru.DmN.pht.std.node.NodeTypes.*
import ru.DmN.pht.std.utils.mapMutable
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.ast.NodeUse
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.node.NodeTypes
import ru.DmN.siberia.node.NodeTypes.USE_CTX
import ru.DmN.siberia.utils.VirtualType

val INodeInfo.processed
    get() = this.withType((this.type as IParsedNodeType).processed)

// a
fun nodeAGet(info: INodeInfo, name: String, index: String) =
    NodeNodesList(info.withType(AGET),
        mutableListOf(nodeGetOrName(info, name), nodeGetOrName(info, index)))
fun nodeArrayOf(info: INodeInfo, elements: MutableList<Node>) =
    NodeNodesList(info.withType(ARRAY_OF), elements)
fun nodeArrayOfType(info: INodeInfo, type: String, elements: MutableList<Node>) =
    NodeNodesList(info.withType(ARRAY_OF_TYPE),
        elements.apply { add(0, nodeValueClass(info, type)) })
fun nodeArraySize(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(ARRAY_SIZE),
        mutableListOf(nodeGetOrName(info, name)))
fun nodeAs(info: INodeInfo, node: Node, type: String) =
    NodeNodesList(info.withType(AS),
        mutableListOf(nodeValueClass(info, type), node))
fun nodeAs(info: INodeInfo, nodes: List<Node>) =
    NodeNodesList(info.withType(AS), nodes.toMutableList())
fun nodeASet(info: INodeInfo, name: String, index: Int, value: Node) =
    NodeNodesList(info.withType(ASET),
        mutableListOf(nodeGetOrName(info, name), nodeValue(info, index), value))
// b
fun nodeBody(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(BODY), nodes)
// c
fun nodeCcall(info: INodeInfo) =
    NodeNodesList(info.withType(CCALL))
fun nodeCls(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(CLS),
        mutableListOf(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
fun nodeCls(info: INodeInfo, name: String, parent: String, node: Node) =
    nodeCls(info, name, listOf(parent), listOf(node))
fun nodeCtor(info: INodeInfo, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(CTOR),
        mutableListOf<Node>(nodeValn(info, args.mapMutable { nodeValn(info, mutableListOf(nodeValue(info, it.first), nodeValueClass(info, it.second))) }))
            .apply { addAll(nodes) })
fun nodeCycle(info: INodeInfo, cond: Node, body: List<Node>) =
    NodeNodesList(info.withType(CYCLE),
        mutableListOf(cond).apply { addAll(body) })
// d
fun nodeDef(info: INodeInfo, fields: List<Pair<String, String>>) =
    NodeNodesList(info.withType(DEF),
        mutableListOf(nodeValn(info, fields.mapMutable { nodeValn(info, mutableListOf(nodeGetOrName(info, it.first), nodeValueClass(info, it.second))) })))
fun nodeDef(info: INodeInfo, name: String, type: String) =
    NodeNodesList(info.withType(DEF),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), nodeValueClass(info, type))))))
fun nodeDef(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(DEF),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(DEFN),
        mutableListOf(
            nodeValue(info, name),
            nodeValue(info, ret),
            nodeValn(info, args.mapMutable { nodeValn(info, mutableListOf(nodeValue(info, it.first), nodeValueClass(info, it.second))) }))
            .apply { addAll(nodes) })
fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, node: Node) =
    nodeDefn(info, name, ret, args, mutableListOf(node))
fun nodeDefn(info: INodeInfo, name: String, ret: String, node: Node) =
    nodeDefn(info, name, ret, emptyList(), mutableListOf(node))
fun nodeDefn(info: INodeInfo, name: String, ret: String, nodes: MutableList<Node>) =
    nodeDefn(info, name, ret, emptyList(), nodes)
// f
fun nodeInitFld(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(FSET_A),
        mutableListOf(nodeGetVariable(info, "this"), nodeGetOrName(info, name), nodeGetOrName(info, name)))
// g
fun nodeGetOrName(info: INodeInfo, name: String) =
    NodeGetOrName(info.withType(GET_OR_NAME), name, false)
fun nodeGetVariable(info: INodeInfo, name: String) =
    NodeGet(info.withType(GET_), name, NodeGet.Type.VARIABLE)
// i
fun nodeInlDef(info: INodeInfo, fields: List<Pair<String, Node>>) =
    NodeNodesList(info.withType(INL_DEF),
        mutableListOf(nodeValn(info, fields.mapMutable { nodeValn(info, mutableListOf(nodeGetOrName(info, it.first), it.second)) })))
fun nodeIf(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(IF), nodes)
// l
fun nodeListOf(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(LIST_OF), nodes)
// m
fun nodeMCall(info: INodeInfo, instance: Node, name: String, args: List<Node>) =
    NodeNodesList(info.withType(MCALL),
        mutableListOf(instance, nodeValue(info, name)).apply { addAll(args) })
fun nodeMCall(info: INodeInfo, type: String, name: String, args: List<Node>) =
    NodeNodesList(info.withType(MCALL),
        mutableListOf<Node>(nodeValueClass(info, type), nodeValue(info, name)).apply { addAll(args) })
// n
fun nodeName(info: INodeInfo, name: String) =
    NodeGetOrName(info.withType(NAME), name, false)
fun nodeNew(info: INodeInfo, type: String, args: List<Node>) =
    NodeNodesList(info.withType(NEW),
        mutableListOf<Node>(nodeValueClass(info, type)).apply { addAll(args) })
fun nodeNewArray(info: INodeInfo, type: String, size: Int) =
    NodeNodesList(info.withType(NEW_ARRAY),
        mutableListOf(nodeValueClass(info, type), nodeValue(info, size)))
// o
fun nodeObj(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(OBJ),
        mutableListOf(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
// t
fun nodeTypesGet(info: INodeInfo, name: String, type: VirtualType) =
    NodeTypedGet(info.withType(TYPED_GET_), name, type)
// u
fun nodeUseCtx(info: INodeInfo, name: String, body: Node) =
    NodeUse(info.withType(USE_CTX), mutableListOf(name), mutableListOf(body))
// v
fun nodeValn(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(VALN), nodes)
fun nodeValn(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(VALN), mutableListOf(node))
fun nodeValueNil(info: INodeInfo) =
    NodeValue.of(info, NIL, "nil")
fun nodeValueClass(info: INodeInfo, name: String) =
    NodeValue.of(info, NodeValue.Type.CLASS, name)
fun nodeValue(info: INodeInfo, value: String) =
    NodeValue.of(info, NodeValue.Type.STRING, value)
fun nodeValue(info: INodeInfo, value: Int) =
    NodeValue.of(info, NodeValue.Type.INT, value.toString())
fun nodeValue(info: INodeInfo, value: Boolean) =
    NodeValue.of(info, NodeValue.Type.BOOLEAN, value.toString())
// w
fun nodeWithGens(info: INodeInfo, node: Node, generics: Sequence<Node>) =
    NodeNodesList(info.withType(WITH_GENS),
        mutableListOf(node).apply { this.addAll(generics) })

// Аннотации
fun nodeInline(info: INodeInfo, node: Node) =
    NodeModifierNodesList(info.withType(ANN_INLINE), mutableListOf(node))
fun nodeStatic(info: INodeInfo, nodes: MutableList<Node>) =
    NodeModifierNodesList(info.withType(ANN_STATIC), nodes)
fun nodeStatic(info: INodeInfo, node: Node) =
    nodeStatic(info, mutableListOf(node))