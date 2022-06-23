package com.linwei.cams.component.mvvm.mvvm.viewmodel

import javax.inject.Inject

class VMProvider @Inject constructor(private val vmMapper: VMClassMapperInterface) :
    VMProviderInterface {

    @Suppress("UNCHECKED_CAST")
    override fun <VM : MvvmViewModel> provideVM(dependant: VMDependant<VM>): VM {
        val concreteClass = vmMapper.getConcreteVMClass(dependant.getVMClass().java)
        return concreteClass as VM
    }
}

interface VMProviderInterface {
    fun <VM : MvvmViewModel> provideVM(dependant: VMDependant<VM>): VM
}