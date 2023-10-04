package ru.DmN.pht.std.base.processor.ctx

import org.objectweb.asm.tree.ClassNode
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.processor.ctx.EnumConstContext

class EnumContext(val type: VirtualType, val enums: MutableList<EnumConstContext> = ArrayList())