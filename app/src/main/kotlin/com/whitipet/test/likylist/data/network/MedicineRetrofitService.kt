package com.whitipet.test.likylist.data.network

import com.whitipet.test.likylist.data.network.entity.RetrofitMedicine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicineRetrofitService {

	@GET("medicine")
	fun getMedicines(): Call<PageResults<RetrofitMedicine>>

	@GET("medicine")
	fun getMedicines(@Query("search") searchQuery: String): Call<PageResults<RetrofitMedicine>>

	@GET("medicine/{id}")
	fun getMedicine(@Path("id") medicineId: Int): Call<RetrofitMedicine>
}