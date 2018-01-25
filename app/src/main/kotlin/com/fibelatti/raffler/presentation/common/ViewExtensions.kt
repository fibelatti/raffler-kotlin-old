package com.fibelatti.raffler.presentation.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commitAllowingStateLoss()
}

@JvmOverloads
fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

@JvmOverloads
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

@JvmOverloads
fun AppCompatActivity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

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

fun Bitmap.changeImageColor(color: Int) {
    val resultBitmap = Bitmap.createBitmap(this, 0, 0,
        this.width - 1, this.height - 1)
    val p = Paint()
    val filter = LightingColorFilter(color, 1)
    p.colorFilter = filter

    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0f, 0f, p)
}

fun Bitmap.covertToDrawable(context: Context): Drawable = BitmapDrawable(context.resources, this)

fun Drawable.convertToBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return this.bitmap
    }

    val bitmap = Bitmap.createBitmap(this.intrinsicWidth,
        this.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)

    return bitmap
}
