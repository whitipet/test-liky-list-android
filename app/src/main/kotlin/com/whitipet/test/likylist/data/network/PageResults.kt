package com.whitipet.test.likylist.data.network

import com.google.gson.annotations.SerializedName

data class PageResults<T>(
	@SerializedName("results") val results: List<T>,
)