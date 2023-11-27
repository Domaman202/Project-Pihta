package ru.DmN.siberia.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.compilers.INodeCompiler

/**
 * Node Universal (Parser/Unparser/Processor) & Compiler
 */
interface INUPC<A : Node, B : Node, C : Node> : INUP<A, B>, INodeCompiler<C>