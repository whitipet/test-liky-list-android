package com.whitipet.test.likylist.data

interface ResponseData<T> {

	fun onResponse(isSuccess: Boolean, data: T?, t: Throwable?)

	fun onSuccess(data: T?) {
		onResponse(true, data, null)
	}

	fun onError(t: Throwable?) {
		onResponse(false, null, t)
	}
}