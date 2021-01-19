package com.whitipet.test.likylist.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		val width = measuredWidth
		setMeasuredDimension(width, width)
	}
}