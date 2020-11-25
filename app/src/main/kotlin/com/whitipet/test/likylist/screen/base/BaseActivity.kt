package com.whitipet.test.likylist.screen.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.utils.StatusBarUtils

abstract class BaseActivity : AppCompatActivity() {

	protected val contentRootView: View by lazy { findViewById(android.R.id.content) }

	override fun onCreate(savedInstanceState: Bundle?) {
		beforeOnCreateSuper()
		super.onCreate(savedInstanceState)

		initBeforeUiCreated()

		setContentView(provideContentView())

		configure(savedInstanceState)
	}

	protected abstract fun provideContentView(): Int


	@CallSuper
	protected open fun beforeOnCreateSuper() {
		val decorView = window.decorView
		decorView.systemUiVisibility = (decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			decorView.systemUiVisibility = (decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
			if (resources.getBoolean(R.bool.is_light_theme)) {
				decorView.systemUiVisibility =
					(decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
			}
		}
		StatusBarUtils.setIconsModeThemeDepending(window)
	}

	@CallSuper
	protected open fun initBeforeUiCreated() {
	}

	protected abstract fun configure(savedInstanceState: Bundle?)

	@CallSuper
	protected open fun updateData() {
	}
}