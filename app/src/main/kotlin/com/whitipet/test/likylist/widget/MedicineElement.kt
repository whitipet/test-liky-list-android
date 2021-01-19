package com.whitipet.test.likylist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.utils.getResourceId
import com.whitipet.test.likylist.utils.obtainStyledAttributes
import com.whitipet.test.likylist.utils.toDp

class MedicineElement @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

	private val tvText: TextView by lazy { findViewById(R.id.tv_medicine_element_text) }

	init {
		inflate(context, R.layout.layout_medicine_element, this)

		layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		orientation = HORIZONTAL
		val insetVertical = 12.toDp()
		setPadding(0, insetVertical, 0, insetVertical)

		attrs.obtainStyledAttributes(context, R.styleable.MedicineElement) {
			getResourceId(R.styleable.MedicineElement_android_src)?.let {
				findViewById<ImageView>(R.id.iv_medicine_element_icon)
					.setImageDrawable(AppCompatResources.getDrawable(getContext(), it))
			}
			getString(R.styleable.MedicineElement_android_title)?.let {
				findViewById<TextView>(R.id.tv_medicine_element_title).text = it
			}
			setText(getString(R.styleable.MedicineElement_android_text))
		}
	}

	fun setText(text: CharSequence?) {
		tvText.text = text
	}
}