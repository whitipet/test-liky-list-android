package com.whitipet.test.likylist.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.whitipet.test.likylist.data.database.Room
import com.whitipet.test.likylist.data.entity.Medicine
import com.whitipet.test.likylist.data.network.PageResults
import com.whitipet.test.likylist.data.network.Retrofit
import com.whitipet.test.likylist.data.network.entity.RetrofitMedicine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MedicineRepository {

	private lateinit var context: Context

	fun setContext(con: Context) {
		context = con
	}

	fun requestMedicines() {
		Retrofit.medicineRetrofitService.getMedicines().enqueue(object : Callback<PageResults<RetrofitMedicine>> {
			override fun onResponse(
				call: Call<PageResults<RetrofitMedicine>>?,
				response: Response<PageResults<RetrofitMedicine>>?,
			) {
				Log.d("MedicineRepository", "getMedicines onResponse: " + response?.body())
				if (response?.body() != null) {
					val retrofitMedicines: List<RetrofitMedicine> = response.body()!!.results
					val medicines: MutableList<Medicine> = mutableListOf()
					for (retrofitMedicine in retrofitMedicines) {
						medicines.add(Medicine(
							retrofitMedicine.id,
							retrofitMedicine.tradeLabel.name,
							retrofitMedicine.manufacturer?.name,
							retrofitMedicine.packaging.description,
							retrofitMedicine.composition.inn.name,
							retrofitMedicine.composition.pharmForm.name,
							retrofitMedicine.composition.description,
						))
					}
					Room.getInstance(context).medicineDao().insert(medicines)
				}
			}

			override fun onFailure(call: Call<PageResults<RetrofitMedicine>>?, t: Throwable?) {
				Log.d("MedicineRepository", "getMedicines onFailure: $t")
			}
		})
	}

	fun requestMedicine(medicineId: String) {
		Retrofit.medicineRetrofitService.getMedicine(medicineId).enqueue(object : Callback<RetrofitMedicine> {
			override fun onResponse(call: Call<RetrofitMedicine>?, response: Response<RetrofitMedicine>?) {
				// TODO:
				Log.d("MedicineRepository", "getMedicine onResponse: " + response?.body())
			}

			override fun onFailure(call: Call<RetrofitMedicine>?, t: Throwable?) {
				Log.d("MedicineRepository", "getMedicine onFailure: $t")
			}
		})
	}

	fun getMedicinesObservable(): LiveData<List<Medicine>> = Room.getInstance(context).medicineDao().getAll()
}