package ru.DmN.pht.std.ast

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.base.utils.Generics
import ru.DmN.pht.std.compiler.java.compilers.NCFunction

class NodeClass(tkOperation: Token, val name: String, val parents: MutableList<String>, nodes: MutableList<Node>, override var generics: Generics = Generics()) : NodeNodesList(tkOperation, nodes), IGenericsContainer {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(tkOperation.text).append(' ').append(name)
        if (parents.isNotEmpty()) {
            builder.append(" :")
            parents.forEach { builder.append(' ').append(it) }
        }
        if (nodes.isNotEmpty())
            builder.append('\n')
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        if (nodes.isNotEmpty())
            builder.indent(indent)
        return builder.append(']')
    }
}