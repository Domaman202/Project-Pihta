package ru.DmN.pht.std.base.utils

import ru.DmN.pht.base.compiler.java.compilers.INodeCompiler
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.INodeParser
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.unparser.unparsers.INodeUnparser

interface IStdUniversalProcessor<T : Node> : INodeParser, INodeUnparser<T>, INodeProcessor<T>, INodeCompiler<T>