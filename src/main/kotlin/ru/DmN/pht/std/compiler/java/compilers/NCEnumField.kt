package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.EnumConstContext
import ru.DmN.pht.base.compiler.java.ctx.EnumContext
import ru.DmN.pht.base.compiler.java.ctx.FieldContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.parser.ast.NodeNodesList

object NCEnumField : NodeCompiler<NodeNodesList>() {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (ctx.type.enum) {
            val cctx = ctx.clazz as EnumContext
            node.nodes.map { compiler.compute<List<Node>>(it, ctx, false) }.forEach {
                val name = compiler.computeStringConst(it.first(), ctx)
                val fnode = cctx.node.visitField(
                    Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM,
                    name,
                    cctx.clazz.desc,
                    null,
                    null
                ) as FieldNode
                val field = VirtualField(name, cctx.clazz, static = true, enum = true)
                cctx.clazz.fields += field
                cctx.fields += FieldContext(fnode, field)
                cctx.enums += EnumConstContext(name, it.drop(1))
            }
        }
        return null
    }
}