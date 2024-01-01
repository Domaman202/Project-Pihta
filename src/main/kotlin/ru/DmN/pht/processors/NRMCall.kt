package ru.DmN.pht.std.processors

import ru.DmN.pht.ast.NodeTypedGet
import ru.DmN.pht.processor.utils.MethodFindResult
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.ast.NodeMCall.Type.*
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.processed
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRMCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val result = findMethod(node, processor, ctx)
        return result.generics ?: processor.calc(getInstance(result, node, processor, ctx), ctx).let { if (it is VTWG) it.gens[0] else result.method.rettype }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeMCall {
        val info = node.info
        val result = findMethod(node, processor, ctx)
        val instance = getInstance(result, node, processor, ctx)
        val generics = result.generics ?: if (result.type == UNKNOWN) null else processor.calc(instance, ctx).let { if (it is VTWG) it.gens[0] else null }
        return if (result.method.extension == null)
            NodeMCall(
                info.processed,
                processArguments(info, processor, ctx, result.method, result.args),
                generics,
                if (result.type == VIRTUAL)
                    NodeFGet(
                        info.withType(NodeTypes.FGET_),
                        mutableListOf(nodeValueClass(info, result.method.declaringClass!!.name)),
                        "INSTANCE",
                        NodeFGet.Type.STATIC,
                        processor.computeType(node.nodes[0], ctx)
                    )
                else instance,
                result.method,
                when (result.type) {
                    UNKNOWN ->
                        if (result.method.modifiers.static)
                            STATIC
                        else VIRTUAL
                    else -> result.type
                }
            )
        else NodeMCall(
            node.info.processed,
            processArguments(
                node.info,
                processor,
                ctx,
                result.method,
                listOf(instance) + result.args
            ),
            generics,
            NodeValue.of(node.info, NodeValue.Type.CLASS, result.method.extension!!.name),
            result.method,
            NodeMCall.Type.EXTEND
        )
    }

    /**
     * Создаёт ноду объекта метод которого будут вызывать.
     *
     * @param result Результат поиска метода.
     * @param node Необработанная нода MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @return Нода.
     */
    private fun getInstance(result: MethodFindResult, node: NodeNodesList, processor: Processor, ctx: ProcessingContext) =
        if (result.type == SUPER)
            nodeGetOrName(node.info, "this")
        else {
            val instance = processor.process(node.nodes[0], ctx, ValType.VALUE)
            if (result.strict && instance is NodeGetOrName)
                NodeTypedGet.of(instance, result.method.declaringClass!!)
            else instance!!
        }

    /**
     * Преобразует исходные аргументы "args" в список нод для передачи в NodeMCall.
     *
     * @param info Информация о родительской ноде.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @param method Метод для которого ведётся преобразование аргументов.
     * @param args Преобразуемые аргументы.
     * @return Преобразованные аргументы.
     */
    fun processArguments(info: INodeInfo, processor: Processor, ctx: ProcessingContext, method: VirtualMethod, args: List<Node>): MutableList<Node> =
        (if (method.modifiers.varargs) {
            val overflow = args.size.let { if (it > 0) it + 1 else 0 } - method.argsc.size
            if (overflow > 0)
                args.dropLast(overflow).toMutableList().apply {
                    val type = method.argsc.last().componentType!!.name
                    add(
                        NRArrayOfType.process(
                            nodeArrayType(info, type, args.asSequence().drop(args.size - overflow).map { nodeAs(info, it, type) }.toMutableList()),
                            processor,
                            ctx,
                            ValType.VALUE
                        )!!
                    )
                }
            else (args + NRNewArray.process(
                nodeNewArray(info, method.argsc.last().name.substring(1), 0),
                processor,
                ctx,
                ValType.VALUE
            )!!).toMutableList()
        } else args)
            .mapIndexed { i, it -> NRAs.process(nodeAs(info, it, method.argsc[i].name), processor, ctx, ValType.VALUE)!! }
            .toMutableList()

    /**
     * Выполняет поиск метода.
     *
     * @param node Необработанная нода MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    private fun findMethod(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): MethodFindResult {
        val gctx = ctx.global
        //
        val pair = getCallTypeAndType(node, processor, ctx, gctx)
        var generic: VirtualType? = null
        val name = processor.computeString(node.nodes[1], ctx).let {
            val gs = it.indexOf('<')
            if (gs < 1)
                return@let it
            generic = gctx.getType(it.substring(gs + 2, it.length - 1), processor.tp)
            it.substring(0, gs)
        }
        val args = node.nodes.asSequence().drop(2).map { processor.process(it, ctx, ValType.VALUE)!! }
        //
        var strict = false
        var result = findMethodOrNull(pair.second, name, args, node, processor, ctx, gctx)
        if (result == null) {
            val types = processor.computeTypesOr(node.nodes[0], ctx) ?: throw RuntimeException("Method '$name' not founded!")
            for (type in types) {
                result = findMethodOrNull(type, name, args, node, processor, ctx, gctx)
                if (result != null) {
                    strict = true
                    break
                }
            }
            result ?: throw RuntimeException("Method '$name' not founded!")
        }
        //
        return MethodFindResult(
            if (pair.first == STATIC)
                if (result.second.modifiers.static)
                    STATIC
                else VIRTUAL
            else pair.first,
            result.first,
            result.second,
            generic,
            strict
        )
    }

    /**
     * Ищет метод подходящий по имени и аргументам, иначе возвращает null.
     *
     * @param clazz Класс для поиска метода.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param node Необработанная нода MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @param gctx GlobalContext.
     */
    private fun findMethodOrNull(clazz: VirtualType, name: String, args: Sequence<Node>, node: NodeNodesList, processor: Processor, ctx: ProcessingContext, gctx: GlobalContext): Pair<List<Node>, VirtualMethod>? {
        return findMethodOrNull(
            clazz,
            name,
            args.toList(),
            processor,
            ctx
        ) ?: if (clazz == VTDynamic)
            findMethod(
                gctx.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                "invokeMethod",
                node.nodes.map { processor.process(it, ctx, ValType.VALUE)!! },
                processor,
                ctx
            )
        else {
            val method = gctx.getMethodVariants(
                (gctx.methods[name]?.asSequence() ?: gctx.methods["*"]?.asSequence()?.filter { it.name == name } ?: return null),
                args.map { ICastable.of(it, processor, ctx) }.toList()
            ).firstOrNull() ?: return null
            Pair(args.mapIndexed { i, it -> processor.adaptToType(method.argsc[i], it, ctx) }.toList(), method)
        }
    }

    /**
     * Определяет тип вызова и класс в котором определён метод.
     *
     * @param node Необработанная нода MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @param gctx GlobalContext.
     * @param (Тип Вызова; Класс Метода)
     */
    private fun getCallTypeAndType(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, gctx: GlobalContext): Pair<NodeMCall.Type, VirtualType> =
        node.nodes[0].let {
            if (it.isConstClass)
                Pair(STATIC, gctx.getType(it.valueAsString, processor.tp))
            else if (it.isLiteral) {
                when (processor.computeString(it, ctx)) {
                    "." -> Pair(UNKNOWN, ctx.clazz)
                    "this" -> Pair(VIRTUAL, processor.calc(it, ctx)!!)
                    "super" -> Pair(SUPER, ctx.clazz.superclass!!)
                    else -> Pair(UNKNOWN, processor.calc(it, ctx)!!)
                }
            } else Pair(UNKNOWN, processor.calc(it, ctx)!!)
        }

    /**
     * Ищет метод, подстраивает ноды аргументов, иначе кидает исключение.
     *
     * @param clazz Класс в котором определён метод.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @return (Аргументы; Метод)
     */
    fun findMethod(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod> =
        findMethodOrNull(clazz, name, args, processor, ctx) ?: throw RuntimeException("Method '$name' not founded!")

    /**
     * Ищет метод, подстраивает ноды аргументов, иначе возвращает null.
     *
     * @param clazz Класс в котором определён метод.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @return (Аргументы; Метод)
     */
    fun findMethodOrNull(clazz: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Pair<List<Node>, VirtualMethod>? {
        val method = ctx.global.getMethodVariants(clazz, name, args.map { ICastable.of(it, processor, ctx) }.toList()).firstOrNull() ?: return null
        return Pair(args.mapIndexed { i, it -> processor.adaptToType(method.argsc[i], it, ctx) }.toList(), method)
    }
}