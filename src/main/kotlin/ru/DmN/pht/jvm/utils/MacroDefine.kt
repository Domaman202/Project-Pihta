package ru.DmN.pht.compiler.java.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.pht.processor.ctx.GlobalContext
import java.util.UUID

class MacroDefine(val uuid: UUID, val name: String, val args: List<String>, val body: List<Node>, val ctx: GlobalContext)