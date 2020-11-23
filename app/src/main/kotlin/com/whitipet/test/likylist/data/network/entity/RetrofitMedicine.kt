package com.whitipet.test.likylist.data.network.entity

import com.google.gson.annotations.SerializedName

data class RetrofitMedicine(
	@SerializedName("id") val id: Int,
	@SerializedName("trade_label") val tradeLabel: TradeLabel,
	@SerializedName("composition") val composition: Composition,
	@SerializedName("manufacturer") val manufacturer: Manufacturer,
	@SerializedName("packaging") val packaging: Packaging,
) {
	data class TradeLabel(
		@SerializedName("name") val name: String,
	)

	data class Composition(
		@SerializedName("description") val description: String,
		@SerializedName("inn") val inn: Inn,
		@SerializedName("pharm_form") val pharmForm: PharmForm,
	) {
		data class Inn(
			@SerializedName("name") val name: String,
		)

		data class PharmForm(
			@SerializedName("name") val name: String,
			@SerializedName("short_name") val shortName: String,
		)
	}

	data class Manufacturer(
		@SerializedName("name") val name: String,
	)

	data class Packaging(
		@SerializedName("description") val description: String,
	)
}