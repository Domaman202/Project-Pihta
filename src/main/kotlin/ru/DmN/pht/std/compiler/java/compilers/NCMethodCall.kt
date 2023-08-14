package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Compiler
import ru.DmN.pht.base.compiler.java.compilers.NodeCompiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.compiler.java.ctx.MethodContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMethodCall
import ru.DmN.pht.std.utils.*

object NCMethodCall : NodeCompiler<NodeMethodCall>() {
    override fun calcType(node: NodeMethodCall, compiler: Compiler, ctx: CompilationContext): VirtualType? =
        if (ctx.type.method)
            calcType(compiler, ctx, compiler.calc(node.nodes[0], ctx)!!.methods.filter { it.name == node.name }, node.nodes.drop(1))
        else null

    fun calcType(compiler: Compiler, ctx: CompilationContext, methods: List<VirtualMethod>, args: List<Node>): VirtualType =
        ctx.gctx.getType(compiler, findFunction(args.map { compiler.calc(it, ctx)!! }, methods, ctx.gctx, compiler).rettype.type)

    override fun compile(node: NodeMethodCall, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? =
        if (ctx.type.method) {
            val instance = compiler.compile(node.nodes[0], ctx, true)!!
            load(instance, ctx.mctx!!.node)
            val type = ctx.gctx.getType(compiler, instance.type!!)
            compileWithRet(
                node,
                compiler,
                ctx,
                ret,
                type,
                node.name,
                node.nodes.drop(1),
                {},
                false
            )
        } else null

    fun compileWithRet(
        node: Node,
        compiler: Compiler,
        ctx: CompilationContext,
        ret: Boolean,
        type: VirtualType,
        name: String,
        args: List<Node>,
        instance: () -> Unit,
        special: Boolean
    ): Variable? = compileRet(node, ctx.mctx!!, ret, compileWithOutRet(compiler, ctx, type, name, args, instance, enumCtor = false, special = special))


    fun compileWithOutRet(
        compiler: Compiler,
        ctx: CompilationContext,
        type: VirtualType,
        name: String,
        args: List<Node>,
        instance: () -> Unit,
        enumCtor: Boolean,
        special: Boolean
    ): VirtualMethod {
        ctx.mctx!!.node.run {
            val filteredMethods = ctx.gctx.getAllExtends(type).let { it + type.methods }.filter { it.name == name }
            val method = findFunction(args.map { compiler.calc(it, ctx)!! }, filteredMethods, ctx.gctx, compiler)
            if (!method.static)
                instance()
            val i =
                if (method.varargs)
                    if (method.argsc.size == 1) args.size
                    else args.size - method.argsc.size
                else 0
            val margs = method.argsc.map { ctx.gctx.getType(compiler, it.type) }
            args.dropLast(i).forEachIndexed { j, it -> loadCast(compiler.compile(it, ctx, true)!!, margs[j], this) }
            if (method.varargs) {
                visitLdcInsn(i)
                visitTypeInsn(
                    Opcodes.ANEWARRAY,
                    margs.last().componentType!!.className
                ) // todo: primitives
                args.drop(args.size - i).forEachIndexed { k, it ->
                    visitInsn(Opcodes.DUP)
                    visitLdcInsn(k)
                    primitiveToObject(compiler.compile(it, ctx, true)!!, this)
                    visitInsn(Opcodes.AASTORE)
                }
            }
            if (method.extend == null) {
                val mtd = method.overrideOrThis()
                val declaringClass = mtd.declaringClass
                visitMethodInsn(
                    if (mtd.static) Opcodes.INVOKESTATIC
                    else if (declaringClass.isInterface) Opcodes.INVOKEINTERFACE
                    else if (special) Opcodes.INVOKESPECIAL
                    else Opcodes.INVOKEVIRTUAL,
                    declaringClass.className,
                    name,
                    if (enumCtor) "(Ljava/lang/String;I${mtd.argsDesc})V" else mtd.desc,
                    declaringClass.isInterface
                )
            } else {
                visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    method.declaringClass.className,
                    name,
                    "(${method.extend!!.desc}${method.argsDesc})${method.rettype.type.desc}",
                    false
                )
            }
            return method
        }
    }

    fun compileRet(node: Node, mctx: MethodContext, ret: Boolean, method: VirtualMethod): Variable? =
        if (method.rettype.type == "void") {
            if (ret) {
                mctx.node.visitFieldInsn(Opcodes.GETSTATIC, "kotlin/Unit", "INSTANCE", "Lkotlin/Unit;")
                Variable("tmp$${node.hashCode()}", "kotlin/Unit", -1, true)
            } else null
        } else if (ret)
            Variable("tmp$${node.hashCode()}", method.rettype.type, -1, true)
        else {
            mctx.node.visitInsn(Opcodes.POP)
            null
        }
}