package com.example.customkeyboard

import android.content.Intent
import android.content.Intent.getIntent
import android.inputmethodservice.InputMethodService
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

abstract class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {
    protected val dispatcher = ServiceLifecycleDispatcher(this)
    var colorIntent = "ff00b3"

    @CallSuper
    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBindInput() {
        super.onBindInput()
        dispatcher.onServicePreSuperOnBind()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        colorIntent = intent!!.getStringExtra("ColorKey").toString()
        return super.onStartCommand(intent, flags, startId)
    }

    @CallSuper
    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }
}