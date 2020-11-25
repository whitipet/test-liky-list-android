package com.whitipet.test.likylist.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivityViewModelFactory<VM : ViewModel, VMF : ViewModelProvider.Factory?> : BaseActivity() {

	protected lateinit var viewModel: VM

	protected open fun provideViewModelFactory(): VMF? = null

	protected abstract fun provideViewModel(): Class<VM>

	private fun initViewModel(vmClass: Class<VM>, vmf: VMF?) {
		viewModel = ViewModelProvider(this, vmf ?: defaultViewModelProviderFactory).get(vmClass)
	}

	override fun initBeforeUiCreated() {
		super.initBeforeUiCreated()
		initViewModel(provideViewModel(), provideViewModelFactory())
	}
}