package ru.DmN.phtx.spt

import kotlin.math.log10

data class DimData (
    val iWidth: Int = 0,
    val iHeight: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var ratio: Float = 0f,
    var widthRatio: Float = 0f,
    var heightRatio: Float = 0f,
    ) {
    fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
        this.ratio = log10((width * height) / (width + height).toFloat()) / 2
        this.widthRatio = width / iWidth.toFloat()
        this.heightRatio = height / iHeight.toFloat()
    }
}