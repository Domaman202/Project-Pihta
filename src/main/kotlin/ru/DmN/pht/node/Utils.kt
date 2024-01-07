package ru.DmN.pht.std.node

import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.utils.mapMutable
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

val INodeInfo.processed
    get() = this.withType((this.type as IParsedNodeType).processed)

// a
fun nodeAGet(info: INodeInfo, name: String, index: String) =
    NodeNodesList(info.withType(NodeTypes.AGET),
        mutableListOf(nodeGetOrName(info, name), nodeGetOrName(info, index)))
fun nodeArrayOf(info: INodeInfo, elements: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_OF), elements)
fun nodeArraySize(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_SIZE),
        mutableListOf(nodeGetOrName(info, name)))
fun nodeArrayType(info: INodeInfo, type: String, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.ARRAY_OF_TYPE), nodes.apply { add(0, nodeValueClass(info, type)) })
fun nodeAs(info: INodeInfo, node: Node, type: String) =
    NodeNodesList(info.withType(NodeParsedTypes.AS),
        mutableListOf(nodeValueClass(info, type), node))
fun nodeAs(info: INodeInfo, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.AS), nodes.toMutableList())
fun nodeASet(info: INodeInfo, name: String, index: Int, value: Node) =
    NodeNodesList(info.withType(NodeParsedTypes.ASET),
        mutableListOf(nodeGetOrName(info, name), nodeValue(info, index), value))
// b
fun nodeBody(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.BODY), nodes)
// c
fun nodeCls(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.CLS),
        mutableListOf(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
fun nodeCls(info: INodeInfo, name: String, parent: String, node: Node) =
    nodeCls(info, name, listOf(parent), listOf(node))
fun nodeCycle(info: INodeInfo, cond: Node, body: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.CYCLE),
        mutableListOf(cond).apply { addAll(body) })
// d
fun nodeDef(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(NodeTypes.DEF),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.DEFN),
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
// g
fun nodeGetOrName(info: INodeInfo, name: String) =
    NodeGetOrName(info.withType(NodeTypes.GET_OR_NAME), name, false)
// i
fun nodeIf(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeTypes.IF), nodes)
// m
fun nodeMCall(info: INodeInfo, instance: Node, name: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.MCALL),
        mutableListOf(instance, nodeValue(info, name)).apply { addAll(args) })
fun nodeMCall(info: INodeInfo, type: String, name: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.MCALL),
        mutableListOf<Node>(nodeValueClass(info, type), nodeValue(info, name)).apply { addAll(args) })
// n
fun nodeNew(info: INodeInfo, type: String, args: List<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.NEW),
        mutableListOf<Node>(nodeValueClass(info, type)).apply { addAll(args) })
fun nodeNewArray(info: INodeInfo, type: String, size: Int) =
    NodeNodesList(info.withType(NodeParsedTypes.NEW_ARRAY),
        mutableListOf(nodeValueClass(info, type), nodeValue(info, size)))
// v
fun nodeValn(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(NodeParsedTypes.VALN), nodes)
fun nodeValn(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(NodeParsedTypes.VALN), mutableListOf(node))
fun nodeValueNil(info: INodeInfo) =
    NodeValue.of(info, NodeValue.Type.NIL, "nil")
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
    NodeNodesList(info.withType(NodeTypes.WITH_GENS),
        mutableListOf(node).apply { this.addAll(generics) })

// Аннотации
fun nodeStatic(info: INodeInfo, nodes: MutableList<Node>) =
    NodeModifierNodesList(info.withType(NodeParsedTypes.ANN_STATIC), nodes)
fun nodeStatic(info: INodeInfo, node: Node) =
    nodeStatic(info, mutableListOf(node))