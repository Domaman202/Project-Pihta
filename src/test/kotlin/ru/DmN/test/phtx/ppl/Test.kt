package ru.DmN.test.phtx.ppl

import ru.DmN.phtx.ppl.Presentation
import ru.DmN.phtx.ppl.element.EImage
import ru.DmN.phtx.ppl.element.EText
import ru.DmN.phtx.ppl.element.ETitle
import ru.DmN.phtx.ppl.page.PageElementList
import java.awt.Color
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.plaf.nimbus.NimbusLookAndFeel

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            darkTheme()
            Presentation("Тестовая Презентация").apply {
                this += PageElementList(
                    listOf(
                        ETitle("Крещение Руси! (I)", 36),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img0.jpg"))),
                    )
                )
                this += PageElementList(
                    listOf(
                        ETitle("Великие Русские Флаги! (II)", 36),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                    )
                )
                this += PageElementList(
                    listOf(
                        ETitle("Слава России! (III)", 36),
                        EText(
                            """
                            1. В 988 году князь Владимир Святославич крестил Русь.
                            2. Событие произошло по приглашению византийского императора Василия II.
                            3. Крещение стало ключевым в истории Древнерусского государства.
                            4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                            5. Христианство оказало влияние на культуру, образование и политику Руси.
                            """.trimIndent(),
                            24
                        ),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg")))
                    )
                )
                this += PageElementList(
                    listOf(
                        ETitle("Слава России! (IV)", 36),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg"))),
                        EText(
                            """
                            1. В 988 году князь Владимир Святославич крестил Русь.
                            2. Событие произошло по приглашению византийского императора Василия II.
                            3. Крещение стало ключевым в истории Древнерусского государства.
                            4. Этот шаг укрепил связи с Византией и положил начало христианской традиции.
                            5. Христианство оказало влияние на культуру, образование и политику Руси.
                            """.trimIndent(),
                            24
                        ),
                    )
                )
                this += PageElementList(
                    listOf(
                        ETitle("Слава России! (V)", 36),
                        EText(
                            """
                            1. Крещение Руси произошло в 988 году.
                            2. Киевская Русь приняла христианство от Византии.
                            3. Князь Владимир Святославич стал первым крещенным правителем.
                            4. Крещение Руси привело к появлению нового мировоззрения народа.
                            5. Христианство стало основой для формирования русской культуры.
                            """.trimIndent(),
                            24
                        ),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img1.jpg"))),
                        EImage(ImageIO.read(Test::class.java.getResourceAsStream("/test/phtx/ppl/img2.jpg"))),
                        ETitle("Слава России! (VI)", 36),
                        EText(
                            """
                            6. Появились первые православные храмы и монастыри на Руси.
                            7. Выведение языческих обрядов и святилищ стало следствием крещения.
                            8. Распространение христианства способствовало укреплению государства.
                            9. Крещение Руси установило тесные связи с православными странами.
                            10. В результате крещения Русь стала частью христианского мира.
                            """.trimIndent(),
                            24
                        )
                    )
                )
                this += PageElementList(
                    listOf(
                        ETitle("Великие Войны России! (V)", 36),
                        EText(
                            """
                            1. Первая война Руси с Османской Империей началась в 1568 году.
                            2. Русская армия сражалась с турецкими войсками на Бесполях.
                            3. В ходе войны Русская земля потеряла несколько городов.
                            4. Во время битвы на реке Оруге русское войско одержало победу.
                            5. Вторая война началась в 1676 году.
                            6. Русское государство стремилось вернуть потерянные земли.
                            7. Битва под Карловыми Вары стала решающей для Русских.
                            8. Петр I провел военные реформы, приготовляясь к новым войнам.
                            9. Третья война началась в 1686 году.
                            10. Русское войско одержало победы под Перекопом и Чигирином.
                            """.trimIndent(),
                            20
                        ),
                        EText(
                            """
                            11. Петр I принял к участию в войне Миссиссиппи Джона.
                            12. Война была завершена Андрусовским миром в 1699 году.
                            13. Четвертая война началась в 1710 году.
                            14. Русское войско под руководством Петра I разгромило турок.
                            15. Андреевское перемирие было заключено в 1713 году.
                            16. Пятая война началась в 1735 году.
                            17. Русская армия потерпела несколько поражений от Османской империи.
                            18. Петр I пытался создать пролив Азов и проникнуть к Черкешу.
                            19. В 1739 году был заключен Белградский мир.
                            20. Шестая война началась в 1768 году.
                            """.trimIndent(),
                            20
                        ),
                        EText(
                            """
                            21. Русское войско воевало в Польше и Молдавии против турок.
                            22. Крым был восстановлен под властью Османской империи.
                            23. Война закончилась Кючук-Кайнарджийским миром в 1774 году.
                            24. Седьмая война началась в 1787 году.
                            25. Русские войска взяли Фоча, постройку Османской империи.
                            26. Османская империя получила сильные укрепления в Хотине.
                            27. Турецкая армия была разгромлена в битве при Рымникском мосте.
                            28. Война была завершена Иасским миром в 1791 году.
                            29. Восьмая война началась в 1806 году.
                            30. Русские войска под руководством Федора Ушакова одержали победу.
                            """.trimIndent(),
                            20
                        )
                    )
                )
                //
                this.start()
            }
        }
    }

    private fun darkTheme() {
        UIManager.setLookAndFeel(NimbusLookAndFeel())
        UIManager.put("control", Color(64, 64, 64))
        UIManager.put("info", Color(64, 64, 64))
        UIManager.put("nimbusBase", Color(18, 30, 49))
        UIManager.put("nimbusAlertYellow", Color(248, 187, 0))
        UIManager.put("nimbusDisabledText", Color(128, 128, 128))
        UIManager.put("nimbusFocus", Color(115, 164, 209))
        UIManager.put("nimbusGreen", Color(176, 179, 50))
        UIManager.put("nimbusInfoBlue", Color(66, 139, 221))
        UIManager.put("nimbusLightBackground", Color(18, 30, 49))
        UIManager.put("nimbusOrange", Color(191, 98, 4))
        UIManager.put("nimbusRed", Color(169, 46, 34))
        UIManager.put("nimbusSelectedText", Color(255, 255, 255))
        UIManager.put("nimbusSelectionBackground", Color(104, 93, 156))
        UIManager.put("text", Color(230, 230, 230))
    }
}