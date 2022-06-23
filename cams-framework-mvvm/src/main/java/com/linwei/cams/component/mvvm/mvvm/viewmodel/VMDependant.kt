package com.linwei.cams.component.mvvm.mvvm.viewmodel

import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

interface VMDependant<VM : MvvmViewModel> : ViewModelStoreOwner {
    fun getVMClass(): KClass<VM>
}