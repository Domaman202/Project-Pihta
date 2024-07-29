package ru.DmN.pht.jvm.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.*
import ru.DmN.pht.jvm.compiler.ctx.body
import ru.DmN.pht.jvm.compiler.ctx.clazz
import ru.DmN.pht.jvm.compiler.ctx.method
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.jvmName
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.utils.normalizeName
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compilers.INodeCompiler
import ru.DmN.siberia.utils.Variable

object NCGet : INodeCompiler<NodeGet> {
    override fun compile(node: NodeGet, compiler: Compiler, ctx: CompilationContext) = Unit
    override fun compileVal(node: NodeGet, compiler: Compiler, ctx: CompilationContext): Variable {
        val name = node.name.normalizeName()
        return when (node.type) {
            VARIABLE -> ctx.body[name] ?: throw RuntimeException("Переменная '$name' не найдена!")
            THIS_FIELD -> ctx.method.node.run {
                visitVarInsn(Opcodes.ALOAD, 0)
                val field = ctx.clazz.clazz.fields.find { it.name == name }!!
                visitFieldInsn(
                    Opcodes.GETFIELD,
                    field.declaringClass.jvmName,
                    name,
                    field.desc
                )
                Variable(name, field.type, -1, true)
            }

            THIS_STATIC_FIELD -> ctx.method.node.run {
                val field = ctx.classes.firstNotNullOf { it -> it.fields.find { it.name == name } }
                visitFieldInsn(
                    Opcodes.GETSTATIC,
                    field.declaringClass.jvmName,
                    name,
                    field.desc
                )
                Variable(node.name, field.type, -1, true)
            }
        }
    }
}