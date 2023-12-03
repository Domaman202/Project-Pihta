package ru.DmN.pht.std.ups

import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NUPUnit : INUPC<Node, Node, Node> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        Node(token)

    override fun unparse(node: Node, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append("(unit)")
    }

    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) =
        Unit

    override fun compileVal(node: Node, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
        return Variable.tmp(node, VirtualType.ofKlass(Unit::class.java))
    }
}