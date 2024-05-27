package ru.DmN.pht.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.vtype.VirtualType

object NCUnit : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) =
        Unit

    override fun compileVal(node: Node, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
        return Variable.tmp("tmp$${node.hashCode()}", VirtualType.ofKlass(Unit::class.java))
    }
}