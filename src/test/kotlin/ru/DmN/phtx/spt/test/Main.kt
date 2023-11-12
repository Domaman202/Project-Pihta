package ru.DmN.phtx.spt.test

import ru.DmN.phtx.spt.Window
import ru.DmN.phtx.spt.components.Image
import ru.DmN.phtx.spt.components.Title
import javax.imageio.ImageIO

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Идут как-то 2 бандерлога - один в кепке, а другой тоже пиZды получит.")

        val window = Window(640, 480)
        window.newPage().let { page ->
            page.add(Title("Крещение Руси", 12))
            page.add(Image(ImageIO.read(Main::class.java.getResourceAsStream("/phtx/spt/img0.jpg")), 20, 50, 280, 380))
            page.add(Image(ImageIO.read(Main::class.java.getResourceAsStream("/phtx/spt/img3.jpg")), 340, 50, 270, 380))
        }
        window.showNext()
    }
}