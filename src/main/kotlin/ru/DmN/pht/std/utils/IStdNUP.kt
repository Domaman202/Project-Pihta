package ru.DmN.pht.std.utils

import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.siberia.ast.Node

interface IStdNUP<A : Node, B : Node> : INUP<A, B>, IStdNodeProcessor<B>