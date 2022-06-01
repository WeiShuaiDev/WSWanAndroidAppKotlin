package com.linwei.cams.component.network.annotation

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RetryDelay(val value: Long = 0)