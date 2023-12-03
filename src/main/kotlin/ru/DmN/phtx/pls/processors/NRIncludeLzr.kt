package ru.DmN.phtx.pls.processors

import com.kingmang.lazurite.parser.ast.AssignmentExpression
import com.kingmang.lazurite.parser.ast.BinaryExpression
import com.kingmang.lazurite.parser.ast.ExprStatement
import com.kingmang.lazurite.parser.ast.FunctionDefineStatement
import com.kingmang.lazurite.parser.ast.FunctionalExpression
import com.kingmang.lazurite.parser.ast.MStatement
import com.kingmang.lazurite.parser.ast.PrintStatement
import com.kingmang.lazurite.parser.ast.ReturnStatement
import com.kingmang.lazurite.parser.ast.Statement
import com.kingmang.lazurite.parser.ast.ValueExpression
import com.kingmang.lazurite.parser.ast.VariableExpression
import com.kingmang.lazurite.parser.pars.FunctionAdder
import com.kingmang.lazurite.parser.pars.Lexer
import com.kingmang.lazurite.parser.pars.Parser
import ru.DmN.pht.std.processor.utils.nodeDefn
import ru.DmN.pht.std.processor.utils.nodeGetOrName
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.processor.utils.nodeValueOf
import ru.DmN.pht.std.utils.computeStringNodes
import ru.DmN.phtx.pls.processor.utils.nodePrognB
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.utils.VTDynamic
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line
import com.kingmang.lazurite.parser.ast.Node as LNode

object NRIncludeLzr : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VTDynamic

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val module = ctx.module
        val line = node.line
        val nodes = processor.computeStringNodes(node, ctx)
            .asSequence()
            .map { module.getModuleFile(it) }
            .map { Lexer.tokenize(it) }
            .map { Parser(it) }
            .map { it.parse().apply { if (it.parseErrors.hasErrors()) throw RuntimeException(it.parseErrors.toString()) } }
            .map { it.apply { accept(FunctionAdder()) } }
            .map { convert(line, it) }
            .toMutableList()
        return NRDefault.process(nodePrognB(line, nodes), processor, ctx, mode)
    }

    fun convert(line: Int, stmt: LNode): Node =
        when (stmt) {
            is MStatement -> nodePrognB(line, stmt.statements.asSequence().map { convert(line, it) }.toMutableList())
            is ExprStatement -> convert(line, stmt.expr)
            is PrintStatement -> NodeNodesList(
                Token.operation(line, "println"),
                mutableListOf(convert(line, stmt.expression))
            )

            is ValueExpression -> when (stmt.value.type()) {
                1 -> nodeValueOf(line, stmt.value.asInt())
                2 -> nodeValueOf(line, stmt.value.asString())
                else -> throw UnsupportedOperationException()
            }

            is BinaryExpression -> NodeNodesList(
                Token.operation(
                    line, when (stmt.operation.toString()) {
                        "+" -> "add"
                        "-" -> "sub"
                        "*" -> "mul"
                        "/" -> "div"
                        "%" -> "rem"
                        "&" -> "and"
                        "|" -> "or"
                        "^" -> "xor"
                        "<< " -> "shift-left"
                        ">>" -> "shift-right"
                        else -> throw UnsupportedOperationException()
                    }
                ),
                mutableListOf(convert(line, stmt.expr1), convert(line, stmt.expr2))
            )

            is AssignmentExpression -> NodeNodesList(
                Token.operation(line, "def-set"),
                mutableListOf(convert(line, stmt.target), convert(line, stmt.expression))
            )

            is VariableExpression -> nodeGetOrName(line, stmt.name)
            is FunctionDefineStatement -> nodeDefn(
                line,
                stmt.name,
                "dynamic",
                stmt.arguments.map {
                    if (it.valueExpr == null) Pair(it.name, "dynamic") else Pair(
                        it.name,
                        (it.valueExpr as VariableExpression).name
                    )
                },
                mutableListOf(convert(line, stmt.body))
            )

            is ReturnStatement -> NodeNodesList(
                Token.operation(line, "ret"),
                mutableListOf(convert(line, stmt.expression))
            )

            is FunctionalExpression -> nodeMCall(
                line,
                nodeGetOrName(line, "."),
                (stmt.functionExpr as VariableExpression).name,
                stmt.arguments.map { convert(line, it) }
            )

            else -> throw UnsupportedOperationException()
        }
}