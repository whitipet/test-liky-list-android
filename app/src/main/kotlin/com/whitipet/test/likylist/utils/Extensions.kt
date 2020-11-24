package com.whitipet.test.likylist.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets

fun View.obtainViewInsets() = Insets.of(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom)

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