package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.siberia.compiler.java.Compiler
import ru.DmN.siberia.compiler.java.compilers.INodeCompiler
import ru.DmN.siberia.compiler.java.utils.CompilationContext
import ru.DmN.siberia.utils.Variable
import ru.DmN.pht.std.compiler.java.utils.body
import ru.DmN.pht.std.compiler.java.utils.clazz
import ru.DmN.pht.std.compiler.java.utils.method
import ru.DmN.pht.std.ast.NodeGetOrName

object NCGetB : INodeCompiler<NodeGetOrName> {
    override fun compileVal(node: NodeGetOrName, compiler: Compiler, ctx: CompilationContext): Variable =
        ctx.body[node.name] ?: ctx.method.node.run {
            var clazz = ctx.clazz.clazz
            var field = clazz.fields.find { it.name == node.name }
            if (field == null) {
                clazz = compiler.tp.typeOf(node.name)
                field = clazz.fields.find { it.name == "INSTANCE" }!!
            }
            if (field.isStatic) {
                visitFieldInsn(Opcodes.GETSTATIC, clazz.className, field.name, field.desc)
            } else {
                visitVarInsn(Opcodes.ALOAD, ctx.body["this"]!!.id)
                visitFieldInsn(Opcodes.GETFIELD, clazz.className, field.name, field.desc)
            }
            Variable.tmp(node, field.type)
        }
}