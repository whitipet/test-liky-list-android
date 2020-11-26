package com.whitipet.test.likylist.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.whitipet.test.likylist.data.MedicineRepository
import com.whitipet.test.likylist.data.ResponseData
import com.whitipet.test.likylist.data.entity.Medicine
import retrofit2.Call

class SearchViewModel : ViewModel() {

	private val _medicinesObservable = MutableLiveData<List<Medicine?>>()
	val medicinesObservable: LiveData<List<Medicine?>>
		get() = Transformations.distinctUntilChanged(_medicinesObservable)
	private val responseData: ResponseData<List<Medicine?>> = object : ResponseData<List<Medicine?>> {
		override fun onResponse(isSuccess: Boolean, data: List<Medicine?>?, t: Throwable?) {
			_medicinesObservable.value = data
		}
	}

	var currentCall: Call<out Any>? = null

	fun search(searchQuery: String?) {
		currentCall?.cancel()
		if (searchQuery.isNullOrBlank()) {
			_medicinesObservable.value = null
		} else {
			currentCall = MedicineRepository.searchMedicines(searchQuery, responseData)
		}
	}
}