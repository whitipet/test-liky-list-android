package com.whitipet.test.likylist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.elevation.ElevationOverlayProvider
import com.whitipet.test.likylist.utils.getThemeColor
import com.whitipet.test.likylist.utils.toDp
import com.google.android.material.R as RM

class SwipeRefreshLayout @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : SwipeRefreshLayout(context, attrs) {

	init {
		setProgressBackgroundColorSchemeColor(getThemeColor(RM.attr.colorSurface))
		setColorSchemeColors(getThemeColor(RM.attr.colorPrimary))
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