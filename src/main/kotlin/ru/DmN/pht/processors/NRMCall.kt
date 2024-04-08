@file:Suppress("UNCHECKED_CAST")
package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.ast.NodeInlBodyB
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.*
import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.pht.jvm.utils.vtype.superclass
import ru.DmN.pht.processor.ctx.*
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.utils.*
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeTypes.FGET_
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.pht.utils.vtype.VVTWithGenerics
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.FINALIZATION
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRMCall : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val instance0 = processor.compute(node.nodes[0], ctx)
        return calc(findMethod(node, Static.ofInstanceNode(instance0, processor, ctx), processor, ctx), instance0, processor, ctx)
    }

    /**
     * Вычисляет тип, который вернёт функция.
     *
     * @param result Результат поиска метода.
     * @param instance Нода instance из MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    fun calc(result: MethodFindResultA, instance: Node, processor: Processor, ctx: ProcessingContext): VirtualType {
        result.method.retgen ?: return result.method.rettype.let { rt ->
            processor.calc(getInstance(result, instance, processor, ctx), ctx).let { it ->
                if (rt is VVTWithGenerics && it is VVTWithGenerics)
                    rt.with(it.gens.filter { it.value.isFirst }.map { Pair(it.key, it.value.first()) }.toMap())
                else rt
            }
        }
        return result.generics
            ?: getGensFromArgs(result, processor, ctx)[result.method.retgen]
            ?: processor.calc(getInstance(result, instance, processor, ctx), ctx).let {
                if (it is VVTWithGenerics) {
                    val gen = it.gens[result.method.retgen]!!
                    if (gen.isFirst)
                        gen.first()
                    else it.generics[gen.second()]!!
                } else result.method.rettype
            }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeMCall {
        val info = node.info
        val instance0 = processor.compute(node.nodes[0], ctx)
        val result = findMethod(node, Static.ofInstanceNode(instance0, processor, ctx), processor, ctx)
        val instance1 = getInstance(result, instance0, processor, ctx)
        val generics = calc(result, node, processor, ctx)
        val method = result.method
        //
        lateinit var arguments: List<Node>
        val new =
            if (method.extension == null) {
                arguments = processArguments(info, processor, ctx, method, result.args, result.compression)
                val instance2 = processor.process(instance1, ctx, true)!!
                NodeMCall(
                    info.processed,
                    arguments,
                    generics,
                    instance2,
                    method,
                    when (result.type) {
                        UNKNOWN ->
                            if (method.modifiers.static)
                                STATIC
                            else VIRTUAL

                        else -> result.type
                    }
                )
            } else {
                arguments = processArguments(
                    node.info,
                    processor,
                    ctx,
                    method,
                    listOf(instance1) + result.args,
                    result.compression
                )
                NodeMCall(
                    node.info.processed,
                    arguments,
                    generics,
                    nodeValueClass(node.info, method.extension!!.name),
                    method,
                    EXTEND
                )
            }
        //
        processor.stageManager.pushTask(FINALIZATION) {
            finalize(method, arguments, instance1, new, processor, ctx, valMode)
        }
        //
        return new
    }

    private fun finalize(method: VirtualMethod, args: List<Node>, instance: Node, node: NodeMCall, processor: Processor, ctx: ProcessingContext, valMode: Boolean) {
        node.inline =
            (method.inline?.copy() ?: processor.inline<Node?>(instance, null, ctx) ?: return).run {
                val bctx = BodyContext.of(ctx.body)
                method.argsn.asSequence().let {
                    if (method.extension == null)
                        it
                    else {
                        NRInlDef.process("this", instance, bctx)
                        it.drop(1)
                    }
                }.forEachIndexed { i, it -> NRInlDef.process(it, args[i], bctx) }
                processor.process(this, (if (this is NodeInlBodyB) this.ctx else ctx).with(bctx), valMode)
            }
    }

    /**
     * Получает generic-и из аргументов.
     *
     * @param result Результат поиска метода.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    private fun getGensFromArgs(result: MethodFindResultA, processor: Processor, ctx: ProcessingContext): Map<String, VirtualType> {
        val map = HashMap<String, VirtualType>()
        val argsg = result.method.argsg
        for (i in argsg.indices)
            map[argsg[i] ?: continue] = processor.calc(result.args[i], ctx)!!
        return map
    }

    /**
     * Создаёт ноду объекта метод которого будут вызывать.
     *
     * @param result Результат поиска метода.
     * @param instance Нода instance из MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     * @return Нода.
     */
    private fun getInstance(result: MethodFindResultA, instance: Node, processor: Processor, ctx: ProcessingContext) =
        if (result.type == SUPER)
            nodeGetVariable(instance.info, "this", result.method.declaringClass)
        else {
            if (result.type == VIRTUAL && instance.isConstClass)
                nodeGetInstance(instance, processor, ctx)
            else {
                val np = processor.get(instance, ctx)
                if (instance.isLiteral && (np as IStdNodeProcessor<Node>).computeString(instance, processor, ctx) == ".")
                    when (result.type) {
                        UNKNOWN -> result.method.run {
                            if (modifiers.static)
                                nodeValueClass(instance.info, declaringClass.name)
                            else throw RuntimeException()
                        }

                        STATIC -> nodeGetInstance(instance, processor, ctx)
                        else -> throw RuntimeException()
                    }
                else if (np is IAdaptableProcessor<*>)
                    (np as IAdaptableProcessor<Node>).adaptToType(result.method.declaringClass, instance, processor, ctx)
                else instance
            }
        }

    /**
     * Создаёт ноду получения INSTANCE от класса в котором определён метод.
     *
     * @param instance Нода instance из MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    private fun nodeGetInstance(instance: Node, processor: Processor, ctx: ProcessingContext): Node {
        val type = processor.computeType(instance, ctx)
        return if (type.name.endsWith("\$Companion")) {
            NodeFGet(
                instance.info.withType(FGET_),
                mutableListOf(nodeValueClass(instance.info, type.name)),
                "Companion",
                NodeFGet.Type.STATIC,
                type
            )
        } else NodeFGet(
            instance.info.withType(FGET_),
            mutableListOf(nodeValueClass(instance.info, type.name)),
            "INSTANCE",
            NodeFGet.Type.STATIC,
            type
        )
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
    fun processArguments(info: INodeInfo, processor: Processor, ctx: ProcessingContext, method: VirtualMethod, args: List<Node>, compression: Boolean = method.modifiers.varargs): MutableList<Node> =
        if (compression) {
            val overflow = args.size.let { if (it > 0) it + 1 else 0 } - method.argsc.size
            if (overflow > 0)
                args.dropLast(overflow).toMutableList().apply {
                    add(
                        processor.process(
                            nodeArrayOfType(
                                info,
                                method.argsc.last().componentType!!.name,
                                args.drop(args.size - overflow)
                            ),
                            ctx,
                            true
                        )!!
                    )
                }
            else (args + processor.process(nodeNewArray(info, method.argsc.last().name.substring(1), 0), ctx, true)!!).toMutableList()
        } else { args }.mapIndexedMutable { i, it ->
            val np = processor.get(it, ctx)
            if (np is IAdaptableProcessor<*>)
                (np as IAdaptableProcessor<Node>).adaptToType(method.argsc[i], it, processor, ctx)
            else { it }.let { processor.process(nodeAs(info, it, method.argsc[i].name), ctx, true)!! }
        }

    /**
     * Выполняет поиск метода.
     *
     * @param node Необработанная нода MCALL.
     * @param static Фильтр статических методов.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    private fun findMethod(node: NodeNodesList, static: Static, processor: Processor, ctx: ProcessingContext): MethodFindResultA {
        val gctx = ctx.global
        //
        val pair = getCallTypeAndType(node, processor, ctx, gctx)
        var generic: VirtualType? = null
        val name = processor.computeString(node.nodes[1], ctx).let {
            val gs = it.indexOf('<')
            if (gs < 1)
                return@let it
            generic = gctx.getType(it.substring(gs + 2, it.length - 1))
            it.substring(0, gs)
        }
        val args = node.nodes.asSequence().drop(2).map { processor.process(it, ctx, true)!! }.toList()
        //
        // Class / Instance
        var result = findMethodOrNull(pair.second, name, args, static, node, processor, ctx)
        // Companion Object
        if (result == null) {
            pair.second.fields.find { it.name == "Companion" }?.let { fld ->
                findMethodOrNull(fld.type, name, args, Static.ANY, node, processor, ctx)?.let {
                    return MethodFindResultA(
                        VIRTUAL,
                        it.args,
                        it.method,
                        generic,
                        false,
                        it.compression
                    )
                }
            }
        }
        // Other
        var strict = false
        if (result == null) {
            val types = sequenceOf(processor.computeTypesOr(node.nodes[0], ctx), ctx.classes)
            for (type in types) {
                result = findMethodOrNull(type, name, args, static, node, processor, ctx)
                if (result != null) {
                    strict = true
                    break
                }
            }
            result ?: throwMNF(pair.second, name, args, processor, ctx)
        }
        //
        return MethodFindResultA(
            if (pair.first == STATIC)
                if (result.method.modifiers.static)
                    STATIC
                else VIRTUAL
            else pair.first,
            result.args,
            result.method,
            generic,
            strict,
            result.compression
        )
    }

    /**
     * Ищет метод подходящий по имени и аргументам, иначе возвращает null.
     *
     * @param clazz Класс для поиска метода.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param static Фильтр статических методов.
     * @param node Необработанная нода MCALL.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    private fun findMethodOrNull(clazz: VirtualType, name: String, args: List<Node>, static: Static, node: NodeNodesList, processor: Processor, ctx: ProcessingContext): MethodFindResultB? {
        findMethodOrNull(clazz, name, args, static, processor, ctx)?.let { return it }

        return if (clazz == VTDynamic) {
            findMethod(
                ctx.global.getType("ru.DmN.pht.utils.DynamicUtils"),
                "invokeMethod",
                node.nodes.map { processor.process(it, ctx, true)!! },
                Static.ANY,
                processor,
                ctx
            )
        } else {
            val gctx = ctx.global
            makeFindResultB(
                args,
                getMethodVariants(
                    (gctx.methods[name] ?: gctx.methods["*"])?.asSequence()?.filter { it.name == name } ?: return null,
                    args.map { ICastable.of(it, processor, ctx) }.toList()
                ),
                static,
                processor,
                ctx
            )
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
                Pair(STATIC, gctx.getType(it.valueAsString))
            else if (it.isLiteral) {
                when (processor.computeString(it, ctx)) {
                    "."     -> Pair(UNKNOWN, ctx.clazz)
                    "this"  -> Pair(VIRTUAL, processor.calc(it, ctx)!!)
                    "super" -> Pair(SUPER, ctx.clazz.superclass!!)
                    else    -> Pair(UNKNOWN, processor.calc(it, ctx)!!)
                }
            } else Pair(UNKNOWN, processor.calc(it, ctx)!!)
        }

    /**
     * Ищет метод, подстраивает ноды аргументов, иначе кидает исключение.
     *
     * @param type Тип в котором определён метод.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param static Фильтр статических методов.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    fun findMethod(type: VirtualType, name: String, args: List<Node>, static: Static, processor: Processor, ctx: ProcessingContext): MethodFindResultB =
        findMethodOrNull(type, name, args, static, processor, ctx) ?: throwMNF(type, name, args, processor, ctx)

    /**
     * Ищет метод, подстраивает ноды аргументов, иначе возвращает null.
     *
     * @param type Тип в котором определён метод.
     * @param name Имя метода.
     * @param args Аргументы.
     * @param static Фильтр статических методов.
     * @param processor Обработчик.
     * @param ctx Контекст обработки.
     */
    fun findMethodOrNull(type: VirtualType, name: String, args: List<Node>, static: Static, processor: Processor, ctx: ProcessingContext): MethodFindResultB? =
        makeFindResultB(
            args,
            ctx.global.getMethodVariants(type, name, args.map { ICastable.of(it, processor, ctx) }),
            static,
            processor,
            ctx
        )

    private fun makeFindResultB(args: List<Node>, find: Sequence<Pair<VirtualMethod, Boolean>>, static: Static, processor: Processor, ctx: ProcessingContext): MethodFindResultB? {
        val result = find.filter { static.filter(it.first) }.firstOrNull() ?: return null
        return MethodFindResultB(
            if (result.second)
                args
            else args.mapIndexed { i, it -> processor.adaptToType(result.first.argsc[i], it, ctx) },
            result.first,
            result.second
        )
    }

    fun throwMNF(type: VirtualType, name: String, args: List<Node>, processor: Processor, ctx: ProcessingContext): Nothing =
        throwMNF(type, name, args.map { processor.calc(it, ctx) })

    fun throwMNF(type: VirtualType, name: String, args: List<VirtualType?>): Nothing {
        val desc = StringBuilder()
        args.forEach { desc.append(it?.desc) }
        throw RuntimeException("Method '$name($desc)${type.desc}' not founded!")
    }
}