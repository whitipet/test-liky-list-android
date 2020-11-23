package com.whitipet.test.likylist.screen.list

import android.os.Bundle
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.screen.base.BaseActivityViewModel

class ListActivity : BaseActivityViewModel<ListViewModel>() {

	override fun provideContentView() = R.layout.activity_list

	override fun provideViewModel(): Class<ListViewModel> = ListViewModel::class.java

	override fun configure(savedInstanceState: Bundle?) {

	}
}