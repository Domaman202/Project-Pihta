package ru.DmN.pht.std.ups

import org.objectweb.asm.Opcodes
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.siberia.Compiler
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.utils.INUPC
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType

object NUPUnit : INUPC<Node, Node, Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) =
        Unit

    override fun compileVal(node: Node, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
        return Variable.tmp(node, VirtualType.ofKlass(Unit::class.java))
    }
}