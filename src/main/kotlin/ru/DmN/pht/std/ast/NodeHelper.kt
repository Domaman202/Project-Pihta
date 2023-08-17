package ru.DmN.pht.std.ast

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ast.Node

class NodeHelper(tkOperation: Token, val calcValue: (node: NodeHelper, compiler: Compiler, ctx: CompilationContext, name: Boolean) -> Any?) : Node(tkOperation)