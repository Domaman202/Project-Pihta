package ru.DmN.pht.std.base.utils

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser

interface INodeUniversalProcessor<A : Node, B : Node> : INodeParser, INodeUnparser<A>, INodeProcessor<B>