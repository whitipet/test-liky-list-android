package com.whitipet.test.likylist.screen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.whitipet.test.likylist.data.MedicineRepository
import com.whitipet.test.likylist.data.entity.Medicine

class ListViewModel : ViewModel() {

	init {
		MedicineRepository.requestMedicines()
	}

	val medicinesObservable: LiveData<List<Medicine>> = MedicineRepository.getMedicinesObservable()
}