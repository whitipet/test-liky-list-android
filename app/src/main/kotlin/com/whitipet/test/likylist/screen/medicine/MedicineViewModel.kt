package com.whitipet.test.likylist.screen.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.whitipet.test.likylist.data.MedicineRepository
import com.whitipet.test.likylist.data.entity.Medicine

class MedicineViewModel(private val medicineId: Int) : ViewModel() {

	init {
		MedicineRepository.requestMedicine(medicineId)
	}

	val medicineObservable: LiveData<Medicine?> = MedicineRepository.getMedicineObservable(medicineId)
}