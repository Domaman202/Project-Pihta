package ru.DmN.pht.cpp.compilers

import ru.DmN.pht.ast.NodeMath
import ru.DmN.pht.cpp.compiler.utils.load
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType.Companion.BOOLEAN

object NCMath : ICppCompiler<NodeNodesList> {
    override fun StringBuilder.compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        when (node.info.type) {
            ADD_ -> compile("+", node.nodes, compiler, ctx)
            SUB_ -> compile("-", node.nodes, compiler, ctx)
            MUL_ -> compile("*", node.nodes, compiler, ctx)
            DIV_ -> compile("/", node.nodes, compiler, ctx)
            REM_ -> compile("%", node.nodes, compiler, ctx)
            NEG_ -> {
                append('-')
                compiler.compileVal(node.nodes[0], ctx).load(this)
            }
            NOT_ -> {
                append('!')
                compiler.compileVal(node.nodes[0], ctx).load(this)
            }
            AND_         -> compile("&",  node.nodes, compiler, ctx)
            OR_          -> compile("|",  node.nodes, compiler, ctx)
            XOR_         -> compile("^",  node.nodes, compiler, ctx)
            SHIFT_LEFT_  -> compile("<<", node.nodes, compiler, ctx)
            SHIFT_RIGHT_ -> compile(">>", node.nodes, compiler, ctx)
            EQ_          -> compile("==", node.nodes, compiler, ctx)
            NOT_EQ_      -> compile("!=", node.nodes, compiler, ctx)
            GREAT_       -> compile(">",  node.nodes, compiler, ctx)
            GREAT_OR_EQ_ -> compile(">=", node.nodes, compiler, ctx)
            LESS_        -> compile("<",  node.nodes, compiler, ctx)
            LESS_OR_EQ_  -> compile("<=", node.nodes, compiler, ctx)
            else         -> throw RuntimeException()
        }
        return Variable.tmp(node, if (node is NodeMath) node.type else BOOLEAN)
    }

    fun StringBuilder.compile(operation: String, nodes: MutableList<Node>, compiler: Compiler, ctx: CompilationContext) {
        append("(")
        compiler.compileVal(nodes.removeFirst(), ctx).load(this)
        append(' ').append(operation).append(' ')
        if (nodes.size == 1)
            compiler.compileVal(nodes.removeFirst(), ctx).load(this)
        else compile(operation, nodes, compiler, ctx)
        append(")")
    }
}