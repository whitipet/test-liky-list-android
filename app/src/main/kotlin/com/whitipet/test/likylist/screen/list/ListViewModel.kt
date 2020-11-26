package com.whitipet.test.likylist.screen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitipet.test.likylist.data.MedicineRepository
import com.whitipet.test.likylist.data.ResponseData
import com.whitipet.test.likylist.data.entity.Medicine

class ListViewModel : ViewModel() {

	init {
		updateData()
	}

	val medicinesObservable: LiveData<List<Medicine>?> = MedicineRepository.getMedicinesObservable()

	private val _dataUpdateIndicatorObservable = MutableLiveData<Void>()
	val dataUpdateIndicatorObservable: LiveData<Void>
		get() = _dataUpdateIndicatorObservable
	private val responseData: ResponseData<List<Medicine?>> = object : ResponseData<List<Medicine?>> {
		override fun onResponse(isSuccess: Boolean, data: List<Medicine?>?, t: Throwable?) {
			_dataUpdateIndicatorObservable.value = null
		}
	}

	fun updateData() {
		MedicineRepository.searchMedicines(responseData)
	}
}