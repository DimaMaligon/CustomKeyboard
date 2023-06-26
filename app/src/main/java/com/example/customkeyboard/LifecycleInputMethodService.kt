package com.example.customkeyboard

import android.content.Intent
import android.content.Intent.getIntent
import android.inputmethodservice.InputMethodService
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

abstract class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {
    protected val dispatcher = ServiceLifecycleDispatcher(this)

    @CallSuper
    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBindInput() {
        super.onBindInput()
        dispatcher.onServicePreSuperOnBind()
    }

    @CallSuper
    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }
}