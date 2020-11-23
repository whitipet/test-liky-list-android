package com.whitipet.test.likylist.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

abstract class BaseActivityViewModel<VM : ViewModel?> : BaseActivityViewModelFactory<VM, NewInstanceFactory?>()