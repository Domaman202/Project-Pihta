package ru.DmN.pht.std.compiler.java.compilers

import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.utils.VirtualMethod
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.desc
import ru.DmN.pht.std.compiler.java.ctx.*
import ru.DmN.pht.std.compiler.java.utils.*
import ru.DmN.pht.std.utils.*

object NCMethodCallA : IStdNodeCompiler<NodeNodesList> {
    override fun calc(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext): VirtualType {
        val instance = compiler.compute(node.nodes[0], ctx, ComputeType.NODE) as Node
        val name = compiler.computeName(node.nodes[1], ctx)
        return calcType(
            compiler,
            ctx,
            (if (instance.isConst()) {
                val cname = node.nodes.first().getConstValueAsString()
                if (ctx.isClass())
                    ctx.clazz.getType(compiler, ctx.global, cname)
                else ctx.global.getType(compiler, cname)
            } else compiler.calc(instance, ctx)!!).methods.filter { it.name == name },
            node.nodes.drop(2)
        )
    }

    fun calcType(compiler: Compiler, ctx: CompilationContext, methods: List<VirtualMethod>, args: List<Node>): VirtualType =
        ctx.global.getType(compiler, findFunction(args.map { compiler.calc(it, ctx)!! }, methods, ctx.global, compiler).rettype.type)

    override fun compile(node: NodeNodesList, compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val instance = compiler.compute(node.nodes[0], ctx, ComputeType.NODE) as Node
        val name = compiler.computeName(node.nodes[1], ctx)
        val type = if (instance.isConstClass()) {
            val cname = node.nodes.first().getConstValueAsString()
            if (ctx.isClass())
                ctx.clazz.getType(compiler, ctx.global, cname)
            else ctx.global.getType(compiler, cname)
        } else {
            val obj = compiler.compile(instance, ctx, true)!!
            load(obj, ctx.method.node)
            ctx.global.getType(compiler, obj.type!!)
        }
        return compileWithRet(
            node,
            compiler,
            ctx,
            ret,
            type,
            name,
            node.nodes.drop(2),
            {},
            false
        )
    }

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
    ): Variable? = compileRet(node, ctx.method, ret, compileWithOutRet(compiler, ctx, type, name, args, instance, enumCtor = false, special = special))


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
        ctx.method.node.run {
            val filteredMethods = ctx.global.getAllExtends(type).let { it + type.methods }.filter { it.name == name }
            val method = findFunction(args.map { compiler.calc(it, ctx)!! }, filteredMethods, ctx.global, compiler)
            if (!method.static)
                instance()
            val i =
                if (method.varargs)
                    if (method.argsc.size == 1) args.size
                    else args.size - method.argsc.size
                else 0
            val margs = method.argsc.map { ctx.global.getType(compiler, it.type) }
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
        if (method.rettype.signature == "void") {
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