package com.kotlin.base.injection

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/*
    组件级别 作用域
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PerComponentScope
