package ru.DmN.pht.base.parser

import ru.DmN.pht.base.utils.Module
import java.util.Stack

class ParsingContext(val modules: MutableList<Module> = ArrayList(), val macros: Stack<String> = Stack())