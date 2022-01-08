package com.whitipet.test.likylist.screen.search

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModel
import com.whitipet.test.likylist.screen.list.MedicinesListAdapter
import com.whitipet.test.likylist.screen.medicine.MedicineActivity
import com.whitipet.test.likylist.utils.*
import com.google.android.material.R as RM

class SearchActivity : BaseActivityViewModel<SearchViewModel>(), View.OnClickListener {

	companion object {
		fun open(context: Context) = context.startActivity(Intent(context, SearchActivity::class.java))
	}

	override fun provideViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

	override fun provideContentView() = R.layout.activity_search

	private val viewSearch: View by lazy { findViewById(R.id.view_search_search) }
	private val btnBack: View by lazy { findViewById(R.id.btn_search_back) }
	private val etSearch: EditText by lazy { findViewById(R.id.et_search) }
	private val btnClear: View by lazy { findViewById(R.id.btn_search_clear) }
	private val rv: RecyclerView by lazy { findViewById(R.id.rv_search) }
	private val emptyView: View by lazy { findViewById(R.id.empty_view_search) }

	override fun configure(savedInstanceState: Bundle?) {
		//region Window insets
		val offsetsViewSearch: Offsets = viewSearch.obtainOffsets()
		val insetsRV: Insets = rv.obtainInsets()
		val insetsEmptyView: Insets = emptyView.obtainInsets()
		ViewCompat.setOnApplyWindowInsetsListener(contentRootView) { _: View?, windowInsets: WindowInsetsCompat? ->
			val insets: Insets = windowInsets?.systemWindowInsets ?: Insets.NONE
			viewSearch.setMargins(offsetsViewSearch.add(insets.removeBottom()))
			val tempInsetsRV: Insets = insetsRV.add(insets)
			rv.setPadding(Insets.of(
				tempInsetsRV.left,
				tempInsetsRV.top + 64.toDp(),
				tempInsetsRV.right,
				tempInsetsRV.bottom,
			))
			val tempInsetsEmptyView: Insets = insetsEmptyView.add(insets)
			emptyView.setPadding(Insets.of(
				tempInsetsEmptyView.left,
				tempInsetsEmptyView.top + 64.toDp(),
				tempInsetsEmptyView.right,
				tempInsetsEmptyView.bottom,
			))
			windowInsets
		}
		//endregion Window insets

		//region Search view background
		val materialShapeDrawable = MaterialShapeDrawable(
			ShapeAppearanceModel.Builder()
				.setAllCornerSizes(4.0f.toDp())
				.build()
		)
		materialShapeDrawable.fillColor = ColorStateList.valueOf(getThemeColor(RM.attr.colorSurface))
		materialShapeDrawable.initializeElevationOverlay(this)
		materialShapeDrawable.elevation = 4.0f.toDp()
		viewSearch.background = materialShapeDrawable
		//endregion Search view background

		btnBack.setOnClickListener(this)
		btnClear.setOnClickListener(this)

		etSearch.requestFocus()
		etSearch.addTextChangedListener(watcher)

		val medicinesListAdapter = MedicinesListAdapter { medicineId, _ ->
			startActivity(MedicineActivity.intent(this, medicineId))
		}
		rv.setHasFixedSize(true)
		rv.adapter = medicinesListAdapter
		viewModel.medicinesObservable.observe(this, {
			medicinesListAdapter.submitList(it) { rv.scrollToPosition(0) }
			emptyView.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
		})
	}

	private val watcher = object : TextWatcher {
		private var searchQuery = ""
		private val searchDebounceRunnable: Runnable by lazy { Runnable { viewModel.search(searchQuery) } }

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			val text = s.toString().trim()
			if (text == searchQuery) return
			searchQuery = text

			etSearch.handler.removeCallbacks(searchDebounceRunnable)
			etSearch.handler.postDelayed(searchDebounceRunnable, 300)
		}

		override fun afterTextChanged(s: Editable?) {
			btnClear.visibility = if (s.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			btnBack.id -> finish()
			btnClear.id -> etSearch.text = null
		}
	}
}