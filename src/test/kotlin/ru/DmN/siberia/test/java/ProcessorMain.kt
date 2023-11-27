package ru.DmN.siberia.test.java

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.TypesProvider
import ru.DmN.siberia.utils.getJavaClassVersion
import ru.DmN.siberia.utils.readAllBytes
import java.io.File

object ProcessorMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val source = Parser(String(ProcessorMain::class.java.getResourceAsStream("/test.pht").readAllBytes())).parseNode(
            ParsingContext.base())!!
        logTxt("pre", source.print())
//        logPht("pre-unparse", Unparser().let { it.unparse(source, UnparsingContext(mutableListOf(Base)), 0); it.out.toString() })
        val processor = Processor(TypesProvider.JAVA)
        val ctx = ProcessingContext.base().with(Platform.JAVA).withJCV(getJavaClassVersion())
        val processed = processor.process(source, ctx, ValType.NO_VALUE)!!
        processor.tasks.forEach {
            ctx.stage.set(it.key)
            it.value.forEach { it() }
        }
        logTxt("post", processed.print())
        val unparsed = Unparser().let { it.unparse(processed, UnparsingContext(mutableListOf(ru.DmN.siberia.Siberia)), 0); it.out.toString() }
        logPht("post-unparse", unparsed)
        val reparsed = Parser(unparsed).parseNode(ParsingContext.base())!!
        logTxt("post-parse", reparsed.print())
    }

    fun logTxt(name: String, text: String) =
        File("dump/$name.txt").writeText(text)
    fun logPht(name: String, code: String) =
        File("dump/$name.pht").writeText(code)

    init {
        File("dump").mkdir()
    }
}