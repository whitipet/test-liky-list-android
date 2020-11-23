package com.whitipet.test.likylist.data

import android.util.Log
import com.whitipet.test.likylist.data.network.entity.RetrofitMedicine
import com.whitipet.test.likylist.data.network.PageResults
import com.whitipet.test.likylist.data.network.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MedicineRepository {

	fun requestMedicines() {
		Retrofit.medicineRetrofitService.getMedicines().enqueue(object : Callback<PageResults<RetrofitMedicine>> {
			override fun onResponse(call: Call<PageResults<RetrofitMedicine>>?, response: Response<PageResults<RetrofitMedicine>>?) {
				// TODO:
				Log.d("MedicineRepository", "getMedicines onResponse: " + response?.body())
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
}