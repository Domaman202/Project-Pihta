package ru.DmN.pht.std.base.compiler.java.utils

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.std.base.processor.ctx.GlobalContext
import java.util.UUID

class MacroDefine(val uuid: UUID, val name: String, val args: List<String>, val body: List<Node>, val ctx: GlobalContext)