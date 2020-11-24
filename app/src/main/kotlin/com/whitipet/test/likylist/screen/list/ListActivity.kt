package com.whitipet.test.likylist.screen.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.*
import androidx.recyclerview.widget.RecyclerView
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModel
import com.whitipet.test.likylist.utils.add
import com.whitipet.test.likylist.utils.obtainViewInsets
import com.whitipet.test.likylist.utils.setMargins
import com.whitipet.test.likylist.utils.setPadding

class ListActivity : BaseActivityViewModel<ListViewModel>() {

	override fun provideContentView() = R.layout.activity_list

	private val rv: RecyclerView by lazy { findViewById(R.id.rv_list) }
	private val btnSearch: View by lazy { findViewById(R.id.btn_list_search) }

	override fun provideViewModel(): Class<ListViewModel> = ListViewModel::class.java

	override fun configure(savedInstanceState: Bundle?) {
		//region Window insets
		val insetsRV: Insets = rv.obtainViewInsets()
		val offsetBtnSearchLeft = btnSearch.marginLeft
		val offsetBtnSearchTop = btnSearch.marginTop
		val offsetBtnSearchRight = btnSearch.marginRight
		val offsetBtnSearchBottom = btnSearch.marginBottom
		ViewCompat.setOnApplyWindowInsetsListener(rootView) { _: View?, windowInsets: WindowInsetsCompat? ->
			val insets: Insets = windowInsets?.stableInsets ?: Insets.NONE
			rv.setPadding(insetsRV.add(insets))
			btnSearch.setMargins(
				offsetBtnSearchLeft + insets.left,
				offsetBtnSearchTop + insets.top,
				offsetBtnSearchRight + insets.right,
				offsetBtnSearchBottom + insets.bottom
			)
			windowInsets
		}
		//endregion Window insets

		val medicinesListAdapter = MedicinesListAdapter { medicine ->
			Log.d("ListActivity", "configure: " + medicine)
		}
		rv.setHasFixedSize(true)
		rv.adapter = medicinesListAdapter
		viewModel.getMedicinesObservable().observe(this, {
			medicinesListAdapter.submitList(it)
		})
	}
}