package com.whitipet.test.likylist

import android.app.Application
import com.whitipet.test.likylist.data.MedicineRepository

open class LikyListApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		MedicineRepository.setContext(applicationContext)
	}
}