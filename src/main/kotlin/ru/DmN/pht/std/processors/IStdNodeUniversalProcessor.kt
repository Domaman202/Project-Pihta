package ru.DmN.pht.std.processors

import ru.DmN.siberia.ast.Node

interface IStdNodeUniversalProcessor<A : Node, B : Node> : INodeUniversalProcessor<A, B>, IStdNodeProcessor<B>