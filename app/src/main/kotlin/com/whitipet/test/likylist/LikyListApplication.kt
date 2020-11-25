package com.whitipet.test.likylist

import android.app.Application
import com.whitipet.test.likylist.data.MedicineRepository

open class LikyListApplication : Application() {

	override fun onCreate() {
		super.onCreate()
		//It should not be so. It's just a quick solution for a test application.
		MedicineRepository.setContext(applicationContext)
	}
}