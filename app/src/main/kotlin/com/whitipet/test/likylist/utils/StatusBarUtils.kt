package com.whitipet.test.likylist.utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.whitipet.test.likylist.R

fun Window.setIconsModeThemeDepending() {
	setIconsMode(context.resources.getBoolean(R.bool.is_light_theme))
}

private fun Window.setIconsMode(lightStatusBar: Boolean) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && lightStatusBar != isIconsDark()) {
		val decor = decorView
		var systemUiVisibility = decor.systemUiVisibility
		systemUiVisibility = if (lightStatusBar) {
			systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		} else {
			systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		}
		decor.systemUiVisibility = systemUiVisibility
	}
	setMIUIStatusBarIconsMode(lightStatusBar)
	setMeizuStatusBarIconsMode(lightStatusBar)
}

@SuppressLint("PrivateApi")
private fun Window.setMIUIStatusBarIconsMode(lightIcon: Boolean) {
	try {
		val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
		val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
		val darkModeFlag = field.getInt(layoutParams)
		val extraFlagField = javaClass.getMethod(
			"setExtraFlags",
			Int::class.javaPrimitiveType,
			Int::class.javaPrimitiveType
		)
		extraFlagField.invoke(this, if (!lightIcon) 0 else darkModeFlag, darkModeFlag)
	} catch (e: Exception) {
		// Log.e("StatusBarUtils", "setMIUIStatusBarIconsMode: $e");
	}
}

private fun Window.setMeizuStatusBarIconsMode(lightIcon: Boolean) {
	try {
		val layoutParams = attributes
		val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
		val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
		darkFlag.isAccessible = true
		meizuFlags.isAccessible = true
		val bit = darkFlag.getInt(null)
		var value = meizuFlags.getInt(layoutParams)
		value = if (!lightIcon) {
			value and bit.inv()
		} else {
			value or bit
		}
		meizuFlags.setInt(layoutParams, value)
		attributes = layoutParams
	} catch (e: Exception) {
		// Log.e("StatusBarUtils", "setMeizuStatusBarIconsMode: $e")
	}
}

private fun Window.isIconsDark(): Boolean {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
	} else false
}