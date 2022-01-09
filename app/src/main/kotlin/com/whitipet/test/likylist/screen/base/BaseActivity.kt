package com.whitipet.test.likylist.screen.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

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
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}

	@CallSuper
	protected open fun initBeforeUiCreated() {
	}

	protected abstract fun configure(savedInstanceState: Bundle?)

	@CallSuper
	protected open fun updateData() {
	}
}