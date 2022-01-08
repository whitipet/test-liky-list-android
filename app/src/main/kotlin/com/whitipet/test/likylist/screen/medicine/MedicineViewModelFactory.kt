package com.whitipet.test.likylist.screen.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MedicineViewModelFactory(private val medicineId: Int) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return MedicineViewModel(medicineId) as T
	}
}