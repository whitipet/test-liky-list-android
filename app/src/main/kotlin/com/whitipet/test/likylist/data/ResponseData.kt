package com.whitipet.test.likylist.data

interface ResponseData<T> {

	fun onResponse(data: T?)
}