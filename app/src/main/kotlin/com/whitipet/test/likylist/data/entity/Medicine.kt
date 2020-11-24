package com.whitipet.test.likylist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class Medicine(
	@PrimaryKey @ColumnInfo(name = "id") val id: Int,
	@ColumnInfo(name = "trade_label") val tradeLabel: String?,
	@ColumnInfo(name = "composition_inn") val inn: String?,
	@ColumnInfo(name = "composition_description") val description: String?,
	@ColumnInfo(name = "packaging_description") val packagingDescription: String?,
	@ColumnInfo(name = "manufacturer_name") val manufacturerName: String?,
	@ColumnInfo(name = "composition_pharm_form") val pharmForm: String?,
)