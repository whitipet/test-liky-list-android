package com.whitipet.test.likylist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.color.MaterialColors
import com.google.android.material.elevation.ElevationOverlayProvider
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.utils.toDp

class SwipeRefreshLayout : SwipeRefreshLayout {

	constructor(context: Context) : super(context) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init()
	}

	private fun init() {
		setProgressBackgroundColorSchemeColor(MaterialColors.getColor(this, R.attr.colorSurface))
		setColorSchemeColors(
			MaterialColors.getColor(this, R.attr.colorPrimary),
			MaterialColors.getColor(this, R.attr.colorPrimaryVariant)
		)
		setProgressBackgroundColorSchemeColor(
			ElevationOverlayProvider(context).compositeOverlayWithThemeSurfaceColorIfNeeded(3f))
	}

	fun setProgressOffset(@Px pixelOffset: Int) {
		val offset = pixelOffset + paddingTop
		setProgressViewOffset(true, offset, offset + 48.toDp())
	}

	override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
		return child.canScrollVertically(-1) && super.onStartNestedScroll(child, target, nestedScrollAxes)
	}

	override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
		return child.canScrollVertically(-1) && super.onStartNestedScroll(child, target, axes, type)
	}
}