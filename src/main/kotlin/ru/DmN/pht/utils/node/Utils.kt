package ru.DmN.pht.utils.node

import ru.DmN.pht.ast.*
import ru.DmN.pht.ast.NodeValue.Type.*
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

val INodeInfo.processed
    get() = this.withType((this.type as IParsedNodeType).processed)

// a
fun nodeAGet(info: INodeInfo, name: String, index: String) =
    NodeNodesList(info.withType(AGET),
        mutableListOf(nodeGetOrName(info, name), nodeGetOrName(info, index)))
fun nodeArrayOfType(info: INodeInfo, type: String, elements: List<Node>) =
    NodeNodesList(info.withType(ARR_OF_TYPE),
        mutableListOf<Node>(nodeValueClass(info, type)).apply { addAll(elements) })
fun nodeArraySize(info: INodeInfo, name: String) =
    NodeNodesList(info.withType(ARR_SIZE),
        mutableListOf(nodeGetOrName(info, name)))
fun nodeAs(info: INodeInfo, node: Node, type: String) =
    NodeNodesList(info.withType(AS),
        mutableListOf(nodeValueClass(info, type), node))
fun nodeASet(info: INodeInfo, name: String, index: Int, value: Node) =
    NodeNodesList(info.withType(ASET),
        mutableListOf(nodeGetOrName(info, name), nodeValue(info, index), value))
// b
fun nodeBGet(info: INodeInfo, name: String, block: String) =
    NodeNodesList(info.withType(BGET), mutableListOf(nodeValue(info, name), nodeValue(info, block)))
fun nodeBSet(info: INodeInfo, name: String, block: String, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(BSET), nodes.apply { add(0, nodeValue(info, block)); add(0, nodeValue(info, name)) })
fun nodeBody(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(BLOCK), nodes)
// c
fun nodeCcall(info: INodeInfo) =
    NodeNodesList(info.withType(CCALL))
fun nodeCls(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(CLS),
        mutableListOf<Node>(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
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
fun nodeDef(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(DEF),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
fun nodeDefSet(info: INodeInfo, name: String, value: Node) =
    NodeNodesList(info.withType(DEF_SET),
        mutableListOf(nodeValn(info, nodeValn(info, mutableListOf(nodeGetOrName(info, name), value)))))
fun nodeDefn(info: INodeInfo, name: String, ret: String, args: List<Pair<String, String>>, nodes: List<Node>) =
    NodeNodesList(info.withType(DEFN),
        mutableListOf<Node>(
            nodeValue(info, name),
            nodeValue(info, ret),
            nodeValn(info, args.mapMutable { nodeValn(info, mutableListOf(nodeValue(info, it.first), nodeValueClass(info, it.second))) })
        ).apply { addAll(nodes) })
fun nodeDefn(info: INodeInfo, name: String, ret: String, nodes: MutableList<Node>) =
    nodeDefn(info, name, ret, emptyList(), nodes)
// f
fun nodeFSet(info: INodeInfo, name: String, instance: Node, values: MutableList<Node>, static: Boolean, native: Boolean) =
    NodeFieldSet(info.withType(FSET_B), values, instance, name, static, native)
fun nodeInitFld(info: INodeInfo, name: String, type: VirtualType) =
    NodeNodesList(info.withType(FSET_A),
        mutableListOf(nodeGetVariable(info, "this", type), nodeGetOrName(info, name), nodeGetOrName(info, name)))
// g
fun nodeGetOrName(info: INodeInfo, name: String) =
    NodeGetOrName(info.withType(GET_OR_NAME), name, false)
fun nodeGetVariable(info: INodeInfo, name: String, type: VirtualType) =
    NodeGet(info.withType(GET_), name, NodeGet.Type.VARIABLE, type)
// i
fun nodeInlDef(info: INodeInfo, fields: List<Pair<String, Node>>) =
    NodeNodesList(info.withType(INL_DEF),
        mutableListOf(nodeValn(info, fields.mapMutable { nodeValn(info, mutableListOf(nodeGetOrName(info, it.first), it.second)) })))
fun nodeIf(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(IF), nodes)
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
    NodeNodesList(info.withType(NEW_ARR),
        mutableListOf(nodeValueClass(info, type), nodeValue(info, size)))
// o
fun nodeObj(info: INodeInfo, name: String, parents: List<String>, nodes: List<Node>) =
    NodeNodesList(info.withType(OBJ),
        mutableListOf<Node>(nodeValue(info, name), nodeValn(info, parents.mapMutable { nodeValue(info, it) })).apply { addAll(nodes) })
// r
fun nodeRollLeft(info: INodeInfo, node: Node, value: Node) =
    NodeNodesList(info.withType(CT_ROLL_LEFT), mutableListOf(node, value))
fun nodeRollRight(info: INodeInfo, node: Node, value: Node) =
    NodeNodesList(info.withType(CT_ROLL_RIGHT), mutableListOf(node, value))
// s
fun nodeSet(info: INodeInfo, name: String, values: List<Node>) =
    NodeSet(info.withType(SET_B), values.toMutableList(), name)
fun nodeSetVariable(info: INodeInfo, name: String, value: Node) =
    NodeSet(info.withType(SET_B), mutableListOf(value), name)
// t
fun nodeThrow(info: INodeInfo, clazz: String, args: List<Node>) =
    NodeNodesList(info.withType(THROW),
        mutableListOf(nodeNew(info, clazz, args)))
fun nodeTypesGet(info: INodeInfo, name: String, type: VirtualType) =
    NodeTGet(info.withType(TGET_), name, type)
fun nodeToEFn(info: INodeInfo, clazz: String, name: String, argsc: List<String>) =
    NodeNodesList(info.withType(TO_EFN),
        mutableListOf(nodeValueClass(info, clazz), nodeGetOrName(info, name), nodeValn(info, argsc.mapMutable { nodeValueClass(info, it) })))
// v
fun nodeValn(info: INodeInfo, nodes: MutableList<Node>) =
    NodeNodesList(info.withType(VALN), nodes)
fun nodeValn(info: INodeInfo, node: Node) =
    NodeNodesList(info.withType(VALN), mutableListOf(node))
fun nodeValueNil(info: INodeInfo) =
    NodeValue(info.withType(VALUE), NIL, "nil")
fun nodeValueClass(info: INodeInfo, name: String) =
    NodeValue(info.withType(VALUE), CLASS, name)
fun nodeValue(info: INodeInfo, value: String) =
    NodeValue(info.withType(VALUE), STRING, value)
fun nodeValue(info: INodeInfo, value: Int) =
    NodeValue(info.withType(VALUE), INT, value.toString())
fun nodeValue(info: INodeInfo, value: Boolean) =
    NodeValue(info.withType(VALUE), BOOLEAN, value.toString())

// Аннотации
fun nodeStatic(info: INodeInfo, nodes: MutableList<Node>) =
    NodeMetaNodesList(info.withType(ANN_STATIC), nodes)
fun nodeStatic(info: INodeInfo, node: Node) =
    nodeStatic(info, mutableListOf(node))
fun nodeTest(info: INodeInfo, node: Node) =
    NodeMetaNodesList(info.withType(ANN_TEST), mutableListOf(node))