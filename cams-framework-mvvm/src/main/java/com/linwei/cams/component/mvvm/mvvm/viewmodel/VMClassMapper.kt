package com.linwei.cams.component.mvvm.mvvm.viewmodel

import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class VMClassMapper @Inject constructor(private val vmClassesMap: MutableMap<Class<out MvvmViewModel>, Provider<MvvmViewModel>>) :
    VMClassMapperInterface {

    @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    override fun getConcreteVMClass(vmClass: Class<out MvvmViewModel>): MvvmViewModel? {
        return vmClassesMap[vmClass]?.get()
    }
}

interface VMClassMapperInterface {
    fun getConcreteVMClass(vmClass: Class<out MvvmViewModel>): MvvmViewModel?
}