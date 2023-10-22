package ru.DmN.pht.std.module.parsers

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.parsers.SimpleNP
import ru.DmN.pht.base.ups.NUPUseCtx
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.module.StdModuleHelper
import ru.DmN.pht.std.module.ast.IValueNode
import ru.DmN.pht.std.module.ast.NodeModule

object NPModule : SimpleNP() {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node {
        val context = ctx.subCtx()
        context.loadedModules.add(0, StdModuleHelper)
        return NPDefault.parse(parser, context) { it ->
            NodeModule(operationToken, it.associate { Pair(it.token.text!!, (it as IValueNode).value) }).apply {
                val name = data["name"] as String
                module = Module.MODULES.getOrPut(name) { Module(name) }
                if (!module.init) {
                    module.init = true
                    (data["files"] as List<String>?)?.let { module.files += it }
                    (data["deps"] as List<String>?)?.let {
                        module.deps += it
                        NUPUseCtx.loadModules(it, parser, ctx)
                    }
                }
            }
        }
    }

}