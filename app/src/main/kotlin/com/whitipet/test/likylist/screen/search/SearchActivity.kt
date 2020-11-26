package com.whitipet.test.likylist.screen.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModel

class SearchActivity : BaseActivityViewModel<SearchViewModel>() {

	companion object {
		fun open(context: Context) = context.startActivity(Intent(context, SearchActivity::class.java))
	}

	override fun provideViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

	override fun provideContentView() = R.layout.activity_search


	override fun configure(savedInstanceState: Bundle?) {

	}
}