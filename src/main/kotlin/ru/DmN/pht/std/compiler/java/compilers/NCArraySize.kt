package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.compiler.java.utils.CompilationContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.utils.load
import ru.DmN.pht.std.compiler.java.utils.method

object NCArraySize : INodeCompiler<NodeNodesList> {
    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable {
        ctx.method.node.run {
            load(compiler.compileVal(node.nodes[0], ctx), this)
            visitInsn(Opcodes.ARRAYLENGTH)
        }
        return Variable.tmp(node, VirtualType.INT)
    }
}