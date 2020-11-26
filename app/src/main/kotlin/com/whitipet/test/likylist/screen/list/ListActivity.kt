package com.whitipet.test.likylist.screen.list

import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModel
import com.whitipet.test.likylist.screen.medicine.MedicineActivity
import com.whitipet.test.likylist.screen.search.SearchActivity
import com.whitipet.test.likylist.utils.*
import com.whitipet.test.likylist.widget.SwipeRefreshLayout

class ListActivity : BaseActivityViewModel<ListViewModel>() {

	override fun provideViewModel(): Class<ListViewModel> = ListViewModel::class.java

	override fun beforeOnCreateSuper() {
		super.beforeOnCreateSuper()
		window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
		window.sharedElementsUseOverlay = false
		setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
	}

	override fun provideContentView() = R.layout.activity_list

	private val srl: SwipeRefreshLayout by lazy { findViewById(R.id.srl_list) }
	private val rv: RecyclerView by lazy { findViewById(R.id.rv_list) }
	private val btnSearch: View by lazy { findViewById(R.id.btn_list_search) }

	override fun configure(savedInstanceState: Bundle?) {
		//region Window insets
		val insetsRV: Insets = rv.obtainInsets()
		val offsetsBtnSearch: Offsets = btnSearch.obtainOffsets()
		ViewCompat.setOnApplyWindowInsetsListener(contentRootView) { _: View?, windowInsets: WindowInsetsCompat? ->
			val insets: Insets = windowInsets?.systemWindowInsets ?: Insets.NONE
			srl.setProgressOffset(insets.top)
			rv.setPadding(insetsRV.add(insets))
			btnSearch.setMargins(offsetsBtnSearch.add(insets))
			windowInsets
		}
		//endregion Window insets

		srl.setOnRefreshListener { viewModel.updateData() }
		viewModel.dataUpdateIndicatorObservable.observe(this, { srl.isRefreshing = false })

		val medicinesListAdapter = MedicinesListAdapter { medicineId, itemView ->
			startActivity(
				MedicineActivity.intent(this, medicineId),
				ActivityOptions.makeSceneTransitionAnimation(this, itemView, "sharedElement").toBundle()
			)
		}
		rv.setHasFixedSize(true)
		rv.adapter = medicinesListAdapter
		viewModel.medicinesObservable.observe(this, { medicinesListAdapter.submitList(it) })

		btnSearch.setOnClickListener { SearchActivity.open(this) }
	}
}