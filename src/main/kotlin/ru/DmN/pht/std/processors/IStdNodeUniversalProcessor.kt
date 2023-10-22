package ru.DmN.pht.std.processors

import ru.DmN.pht.base.ast.Node

interface IStdNodeUniversalProcessor<A : Node, B : Node> : INodeUniversalProcessor<A, B>, IStdNodeProcessor<B>