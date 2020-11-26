package com.whitipet.test.likylist.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.utils.toDp

class MedicineElement : LinearLayout {

	//region
	constructor(context: Context) : super(context) {
		init(context, null)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		init(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
			super(context, attrs, defStyleAttr, defStyleRes) {
		init(context, attrs)
	}
	//endregion

	private lateinit var tvText: TextView

	private fun init(context: Context, attrs: AttributeSet?) {
		LayoutInflater.from(context).inflate(R.layout.layout_medicine_element, this, true)
		layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		orientation = HORIZONTAL
		val insetVertical = 12.toDp()
		setPadding(0, insetVertical, 0, insetVertical)
		tvText = findViewById(R.id.tv_medicine_element_text)

		val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MedicineElement)
		try {
			val iconId: Int = typedArray.getResourceId(R.styleable.MedicineElement_android_src, -1)
			if (iconId != -1) {
				val drawable = AppCompatResources.getDrawable(getContext(), iconId)
				findViewById<ImageView>(R.id.iv_medicine_element_icon).setImageDrawable(drawable)
			}
			findViewById<TextView>(R.id.tv_medicine_element_title).text =
				typedArray.getString(R.styleable.MedicineElement_android_title)
			setText(typedArray.getString(R.styleable.MedicineElement_android_text))
		} finally {
			typedArray.recycle()
		}
	}

	fun setText(text: CharSequence?) {
		tvText.text = text
	}
}
