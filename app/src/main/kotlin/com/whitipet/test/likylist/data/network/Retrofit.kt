package com.whitipet.test.likylist.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

	private var retrofit: Retrofit = Retrofit.Builder()
		.baseUrl("https://api.pills-prod.sdh.com.ua/v1/")
		.client(OkHttpClient.Builder().build())
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	val medicineRetrofitService: MedicineRetrofitService by lazy { retrofit.create(MedicineRetrofitService::class.java); }
}