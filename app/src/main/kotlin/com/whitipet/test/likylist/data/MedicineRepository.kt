package com.whitipet.test.likylist.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

	fun setContext(context: Context) {
		this.context = context
	}

	fun requestMedicines(responseData: ResponseData<List<Medicine?>>? = null) {
		Retrofit.medicineRetrofitService.getMedicines().enqueue(object : Callback<PageResults<RetrofitMedicine>> {
			override fun onResponse(
				call: Call<PageResults<RetrofitMedicine>>?,
				response: Response<PageResults<RetrofitMedicine>>?,
			) {
				val medicines: List<Medicine?> = (response?.body()?.results ?: emptyList()).map {
					mapRetrofitMedicineToMedicine(it)
				}
				Room.getInstance(context).medicineDao().insert(medicines)
				responseData?.onSuccess(medicines)
			}

			override fun onFailure(call: Call<PageResults<RetrofitMedicine>>?, t: Throwable?) {
				responseData?.onError(t)
			}
		})
	}

	fun requestMedicines(searchQuery: String, responseData: ResponseData<List<Medicine?>>? = null) {
		Retrofit.medicineRetrofitService.getMedicines(searchQuery)
			.enqueue(object : Callback<PageResults<RetrofitMedicine>> {
				override fun onResponse(
					call: Call<PageResults<RetrofitMedicine>>?,
					response: Response<PageResults<RetrofitMedicine>>?,
				) {
					val medicines: List<Medicine?> = (response?.body()?.results ?: emptyList()).map {
						mapRetrofitMedicineToMedicine(it)
					}
					responseData?.onSuccess(medicines)
				}

				override fun onFailure(call: Call<PageResults<RetrofitMedicine>>?, t: Throwable?) {
					responseData?.onError(t)
				}
			})
	}

	fun requestMedicine(medicineId: Int) {
		Retrofit.medicineRetrofitService.getMedicine(medicineId).enqueue(object : Callback<RetrofitMedicine> {
			override fun onResponse(call: Call<RetrofitMedicine>?, response: Response<RetrofitMedicine>?) {
				Room.getInstance(context).medicineDao().insert(mapRetrofitMedicineToMedicine(response?.body()))
			}

			override fun onFailure(call: Call<RetrofitMedicine>?, t: Throwable?) {
				// TODO
			}
		})
	}

	fun getMedicinesObservable(): LiveData<List<Medicine>> =
		Transformations.distinctUntilChanged(Room.getInstance(context).medicineDao().getAll())

	fun getMedicineObservable(medicineId: Int): LiveData<Medicine> =
		Transformations.distinctUntilChanged(Room.getInstance(context).medicineDao().getById(medicineId))

	private fun mapRetrofitMedicineToMedicine(retrofitMedicine: RetrofitMedicine?): Medicine? {
		if (retrofitMedicine != null) {
			return Medicine(
				retrofitMedicine.id,
				retrofitMedicine.tradeLabel?.name,
				retrofitMedicine.composition?.inn?.name,
				retrofitMedicine.composition?.description,
				retrofitMedicine.packaging?.description,
				retrofitMedicine.manufacturer?.name,
				retrofitMedicine.composition?.pharmForm?.name,
			)
		}
		return null
	}
}