package com.whitipet.test.likylist.data.entity

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
) {

	@Ignore
	private var _color: Int? = null
	val color: Int
		get() {
			// Generate a color that can be used as a placeholder
			if (_color == null) {
				val colorDependsOn = tradeLabel ?: ""
				val colorDependsOnHex = Integer.toHexString(colorDependsOn.hashCode()).padEnd(6, '0').substring(0, 6)
				val rawColor = Color.parseColor("#$colorDependsOnHex")
				val color = Color.argb(64, Color.red(rawColor), Color.green(rawColor), Color.blue(rawColor))
				_color = color
			}
			return _color!!
		}
}