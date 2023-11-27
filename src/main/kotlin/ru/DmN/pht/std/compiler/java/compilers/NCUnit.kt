package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.compilers.INodeCompiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.method

object NCUnit : INodeCompiler<Node> {
    override fun compile(node: Node, compiler: Compiler, ctx: CompilationContext) = Unit

    override fun compileVal(node: Node, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
        return Variable.tmp("tmp$${node.hashCode()}", VirtualType.ofKlass(Unit::class.java))
    }
}