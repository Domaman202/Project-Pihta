package ru.DmN.pht.std.collections.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.TypeInsnNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.compiler.java.utils.global
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.utils.calcType
import ru.DmN.pht.std.utils.ofPrimitive
import ru.DmN.pht.std.utils.primitiveToObject

object NCArrayOf : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType? {
        var type: VirtualType? = null
        node.nodes.forEach { type = calcType(type, ctx.global.getType(compiler, compiler.calc(it, ctx)!!.ofPrimitive()!!)).first }
        return type!!.arrayType
    }

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ret)
            ctx.method.node.run {
                var type: VirtualType? = null
                visitLdcInsn(node.nodes.size)
                visitInsn(Opcodes.NOP)
                val inst = instructions.last
                node.nodes.forEachIndexed { i, it ->
                    visitInsn(Opcodes.DUP)
                    visitLdcInsn(i)
                    type = calcType(type, VirtualType.ofKlass(primitiveToObject(compiler.compile(it, ctx, true)!!, this)!!)).first
                    visitInsn(Opcodes.AASTORE)
                }
                instructions.set(inst, TypeInsnNode(Opcodes.ANEWARRAY, type!!.className))
                Variable("pht$${node.hashCode()}", "[${type!!.name}", -1, true)
            }
        else null
}