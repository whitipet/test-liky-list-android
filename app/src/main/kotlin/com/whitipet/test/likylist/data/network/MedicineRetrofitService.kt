package com.whitipet.test.likylist.data.network

import com.whitipet.test.likylist.data.network.entity.RetrofitMedicine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MedicineRetrofitService {

	@GET("medicine")
	fun getMedicines(): Call<PageResults<RetrofitMedicine>>

	@GET("medicine/{id}")
	fun getMedicine(@Path("id") medicineId: String): Call<RetrofitMedicine>
}