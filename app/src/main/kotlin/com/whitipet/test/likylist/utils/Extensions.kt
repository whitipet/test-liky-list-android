package com.whitipet.test.likylist.utils

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.Insets
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.google.android.material.color.MaterialColors

//region Insets/Offsets
fun View.obtainInsets() = Insets.of(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom)

typealias Offsets = Insets

fun View.obtainOffsets() = Insets.of(this.marginLeft, this.marginTop, this.marginRight, this.marginBottom)

fun View.setPadding(insets: Insets) = this.setPadding(insets.left, insets.top, insets.right, insets.bottom)

fun View.setMargins(offsets: Offsets) {
	this.setMargins(offsets.left, offsets.top, offsets.right, offsets.bottom)
}

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
//endregion Insets/Offsets

//region Attrs
fun AttributeSet?.obtainStyledAttributes(context: Context, attrs: IntArray, block: TypedArray.() -> Unit) {
	this?.let { context.withStyledAttributes(it, attrs, 0, 0, block) }
}

fun TypedArray.getResourceId(@StyleableRes index: Int): Int? {
	return if (hasValue(index)) getResourceId(index, -1) else null
}
//endregion Attrs

//region View
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
//endregion View

//region Display metrics
fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.toDp(): Float = this * Resources.getSystem().displayMetrics.density
//endregion Display metrics

//region Color
@ColorInt
fun View.getThemeColor(@AttrRes colorAttributeResId: Int): Int =
	MaterialColors.getColor(this, colorAttributeResId, Color.MAGENTA)
//endregion Color

//region Fraction
fun Float.subFraction(
	@FloatRange(from = 0.0, to = 1.0) newFractionFrom: Float,
	@FloatRange(from = 0.0, to = 1.0) newFractionTo: Float,
): Float {
	return if (newFractionFrom >= newFractionTo) {
		0.0f
	} else {
		(this - newFractionFrom) / (newFractionTo - newFractionFrom).coerceIn(0.0f, 1.0f)
	}
}
//endregion Fraction