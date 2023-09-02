package ru.DmN.pht.std.decl.compiler.java.compilers

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.*
import ru.DmN.pht.std.base.compiler.java.compilers.IStdNodeCompiler
import ru.DmN.pht.std.base.compiler.java.utils.ComputeType
import ru.DmN.pht.std.base.compiler.java.utils.compute
import ru.DmN.pht.std.base.compiler.java.utils.computeName
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.decl.compiler.java.utils.with

object NCClass : IStdNodeCompiler<NodeNodesList> {
    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val gctx = ctx.global
        val parts = node.nodes.map { { type: ComputeType -> compiler.compute<Any?>(it, ctx, type) } }
        val name = gctx.name(parts[0](ComputeType.NAME) as String)
        val parents = (parts[1](ComputeType.NODE) as List<Node>)
            .map { compiler.computeName(it, ctx) }
            .map { gctx.getType(compiler, it) }.toMutableList()
        //
        val isInterface = node.tkOperation.text!!.endsWith("intf")
        val isClass = node.tkOperation.text.endsWith("cls")
        val isObject = node.tkOperation.text.endsWith("obj")
        //
        val generics = Generics()
        (node.attributes.getOrDefault(
            "generics",
            emptyList<(Pair<String, String>)>()
        ) as List<Pair<String, String>>).forEach {
            generics.list += Generic(it.first, it.second)
        }
        //
        val type = VirtualType(name, parents, isInterface = isInterface, generics = generics)
        compiler.types += type
        compiler.pushTask(ctx, CompileStage.TYPES_DEFINE) {
            if (isObject)
                type.fields += VirtualField("INSTANCE", type, static = true, enum = false)
            if (parts.size > 2) {
                compiler.pushTask(ctx, CompileStage.METHODS_PREDEFINE) {
                    compiler.compile(parts[2](ComputeType.NODE) as Node, ctx.with(type), false)
                }
            }
        }
        //
        return null
    }
}