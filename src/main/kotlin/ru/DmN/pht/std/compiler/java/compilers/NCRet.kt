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

object NCRet : INodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext) {
        ctx.method.node.run {
            val result = compiler.compileVal(node.nodes[0], ctx)
            load(result, this)
            visitInsn(
                when (result.type!!) {
                    VirtualType.BOOLEAN,
                    VirtualType.BYTE,
                    VirtualType.SHORT,
                    VirtualType.CHAR,
                    VirtualType.INT -> Opcodes.IRETURN

                    VirtualType.LONG -> Opcodes.LRETURN
                    VirtualType.FLOAT -> Opcodes.FRETURN
                    VirtualType.DOUBLE -> Opcodes.DRETURN
                    else -> Opcodes.ARETURN
                }
            )
        }
    }

    override fun compileVal(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): Variable =
        compiler.compileVal(node.nodes[0], ctx)
}