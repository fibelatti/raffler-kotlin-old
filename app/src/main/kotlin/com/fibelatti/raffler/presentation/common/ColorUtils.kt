package com.fibelatti.raffler.presentation.common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

fun calculateColorGradient(startColor: Int, endColor: Int, steps: Int): List<Int> {
    val r1 = Color.red(startColor)
    val g1 = Color.green(startColor)
    val b1 = Color.blue(startColor)

    val r2 = Color.red(endColor)
    val g2 = Color.green(endColor)
    val b2 = Color.blue(endColor)

    val redStep = (r2 - r1) / steps
    val greenStep = (g2 - g1) / steps
    val blueStep = (b2 - b1) / steps

    return (0..steps).map { Color.rgb(r1 + redStep * it, g1 + greenStep * it, b1 + blueStep * it) }
}

fun changeImageColor(sourceBitmap: Bitmap, color: Int): Bitmap {
    val resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
        sourceBitmap.width - 1, sourceBitmap.height - 1)
    val p = Paint()
    val filter = LightingColorFilter(color, 1)
    p.colorFilter = filter

    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0f, 0f, p)
    return resultBitmap
}

fun covertBitmapToDrawable(context: Context, bitmap: Bitmap): Drawable {
    return BitmapDrawable(context.resources, bitmap)
}

fun convertDrawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}
