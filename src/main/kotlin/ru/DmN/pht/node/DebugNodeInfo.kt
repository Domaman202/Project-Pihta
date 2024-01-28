package ru.DmN.pht.node

import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.node.INodeType

class DebugNodeInfo(val info: INodeInfo) : INodeInfo {
    override val file: String?
        get() = info.file
    override val line: Int?
        get() = info.line
    override val type: INodeType
        get() = info.type

    override fun withType(type: INodeType): INodeInfo =
        DebugNodeInfo(info.withType(type))

    override fun print() {
        println("""
            [
            | type: $type
            | file: $file
            | line: $line
            ]
        """.trimIndent())
    }
}