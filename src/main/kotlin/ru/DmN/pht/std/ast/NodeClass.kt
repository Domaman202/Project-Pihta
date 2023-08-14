package ru.DmN.pht.std.ast

import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.ctx.ClassContext
import ru.DmN.pht.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.indent
import ru.DmN.pht.base.utils.Generics

class NodeClass(tkOperation: Token, val name: String, val parents: MutableList<String>, nodes: MutableList<Node>, override val generics: Generics = Generics()) : NodeNodesList(tkOperation, nodes), IGenericsContainer {
    fun getSignature(compiler: Compiler, cctx: ClassContext, gctx: GlobalContext): String? {
        val gensig = generics.getSignature(compiler, gctx)
        return if (gensig.isNotEmpty()) {
            val sb = StringBuilder()
            sb.append('<').append(gensig).append('>')
            parents.forEach { sb.append(cctx.getSignature(cctx.getType(compiler, gctx, it))) }
            sb.toString()
        } else null
    }

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