package com.whitipet.test.likylist

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

class StatusBarUtils {

	@Suppress("unused")
	companion object {
		fun setDarkIconsMode(window: Window) {
			setIconsMode(window, true)
		}

		fun setLightIconsMode(window: Window) {
			setIconsMode(window, false)
		}

		fun setIconsModeThemeDepending(window: Window) {
			setIconsMode(window, window.context.resources.getBoolean(R.bool.is_light_theme))
		}

		private fun setIconsMode(window: Window, lightStatusBar: Boolean) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && lightStatusBar != isIconsDark(window)) {
				val decor = window.decorView
				var systemUiVisibility = decor.systemUiVisibility
				systemUiVisibility = if (lightStatusBar) {
					systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
				} else {
					systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
				}
				decor.systemUiVisibility = systemUiVisibility
			}
			setMIUIStatusBarIconsMode(window, lightStatusBar)
			setMeizuStatusBarIconsMode(window, lightStatusBar)
		}

		@SuppressLint("PrivateApi")
		private fun setMIUIStatusBarIconsMode(window: Window, lightIcon: Boolean) {
			try {
				val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
				val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
				val darkModeFlag = field.getInt(layoutParams)
				val extraFlagField = window.javaClass.getMethod(
					"setExtraFlags",
					Int::class.javaPrimitiveType,
					Int::class.javaPrimitiveType
				)
				extraFlagField.invoke(window, if (!lightIcon) 0 else darkModeFlag, darkModeFlag)
			} catch (e: Exception) {
				//Log.e("StatusBarUtils", "setMIUIStatusBarIconsMode: " + e);
			}
		}

		private fun setMeizuStatusBarIconsMode(window: Window, lightIcon: Boolean) {
			try {
				val layoutParams = window.attributes
				val darkFlag =
					WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
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
				window.attributes = layoutParams
			} catch (e: Exception) {
				//Log.e("StatusBarUtils", "setMeizuStatusBarIconsMode: " + e);
			}
		}

		private fun isIconsDark(window: Window): Boolean {
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				(window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
						== View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
			} else false
		}
	}
}