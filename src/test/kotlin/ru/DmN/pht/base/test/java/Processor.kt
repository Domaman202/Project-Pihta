package ru.DmN.pht.base.test.java

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.processor.utils.*
import ru.DmN.pht.base.test.UnparserMain
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.with
import java.io.File
import kotlin.math.log

object Processor {
    @JvmStatic
    fun main(args: Array<String>) {
        val source = Parser(String(UnparserMain::class.java.getResourceAsStream("/test.pht").readAllBytes())).parseNode(ParsingContext.base())!!
        logTxt("pre", source.print())
        val processor = Processor(JavaTypesProvider())
        val ctx = ProcessingContext.base().with(Platform.JAVA)
        val processed = processor.process(source, ctx, ValType.NO_VALUE)!!
        processor.tasks.forEach {
            ctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        logTxt("post", processed.print())
        val unparsed = Unparser().let { it.unparse(processed, UnparsingContext(mutableListOf(Base)), 0); it.out.toString() }
        logPht("post-unparse", unparsed)
        val reparsed = Parser(unparsed).parseNode(ParsingContext.base())!!
        logTxt("post-parse", reparsed.print())
    }

    fun logTxt(name: String, text: String) =
        File("log/$name.txt").writeText(text)
    fun logPht(name: String, code: String) =
        File("log/$name.pht").writeText(code)

    init {
        File("log").mkdir()
    }
}