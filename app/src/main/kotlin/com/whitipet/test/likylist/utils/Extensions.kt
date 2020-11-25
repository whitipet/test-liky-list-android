package com.whitipet.test.likylist.utils

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.FloatRange
import androidx.core.graphics.Insets

fun View.obtainInsets() = Insets.of(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom)

fun View.setPadding(insets: Insets) = this.setPadding(insets.left, insets.top, insets.right, insets.bottom)

fun Insets.add(insets: Insets): Insets = Insets.of(
	this.left + insets.left,
	this.top + insets.top,
	this.right + insets.right,
	this.bottom + insets.bottom
)

fun Insets.removeTop(): Insets = Insets.of(
	this.left,
	0,
	this.right,
	this.bottom,
)


fun Insets.removeBottom(): Insets = Insets.of(
	this.left,
	this.top,
	this.right,
	0,
)

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
	val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
	lp.setMargins(
		left ?: lp.leftMargin,
		top ?: lp.topMargin,
		right ?: lp.rightMargin,
		bottom ?: lp.rightMargin
	)
	layoutParams = lp
}

fun Float.subfraction(
	@FloatRange(from = 0.0, to = 1.0) newFractionFrom: Float,
	@FloatRange(from = 0.0, to = 1.0) newFractionTo: Float,
): Float {
	return if (newFractionFrom >= newFractionTo) {
		0.0f
	} else {
		(this - newFractionFrom) / (newFractionTo - newFractionFrom).coerceIn(0.0f, 1.0f)
	}
}

fun View.requestSize(onGotViewSize: (Int, Int) -> Unit) {
	val v: View = this
	v.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
		override fun onGlobalLayout() {
			val width = v.width
			val height = v.height
			if (width != 0 || height != 0) {
				v.viewTreeObserver.removeOnGlobalLayoutListener(this)
			}
			onGotViewSize(width, height)
		}
	})
}