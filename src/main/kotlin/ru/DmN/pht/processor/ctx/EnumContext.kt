package ru.DmN.pht.processor.ctx

import ru.DmN.siberia.utils.vtype.VirtualType

class EnumContext(val type: VirtualType, val enums: MutableList<EnumConstContext> = ArrayList())