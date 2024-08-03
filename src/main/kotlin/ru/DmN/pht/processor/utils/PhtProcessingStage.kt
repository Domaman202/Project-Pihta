package ru.DmN.pht.processor.utils

import ru.DmN.siberia.processor.utils.ProcessingStage.MODULE_POST_INIT
import ru.DmN.siberia.utils.stage.IStage
import ru.DmN.siberia.utils.stage.StageManager

enum class PhtProcessingStage : IStage {
    /**
     * Определение макросов.
     */
    MACROS_DEFINE,

    /**
     * Импорт макросов.
     */
    MACROS_IMPORT,

    /**
     * Импорт типов.
     */
    TYPES_IMPORT,

    /**
     * Предопределение типов.
     */
    TYPES_PREDEFINE,

    /**
     * Определение типов.
     */
    TYPES_DEFINE,

    /**
     * Импорт расширений.
     */
    EXTENSIONS_IMPORT,

    /**
     * Обработка тел конструкторов.
     */
    CONSTRUCTORS_BODY,

    /**
     * Обработка тел методов.
     */
    METHODS_BODY;

    companion object {
        fun addStagesAfter(sm: StageManager, after: IStage = MODULE_POST_INIT) {
            val entries = entries
            if (sm.containsStage(entries[0]))
                return
            sm.addStageAfter(entries[0], after)
            for (i in 1..<entries.size) {
                sm.addStageAfter(entries[i], entries[i - 1])
            }
        }
    }
}