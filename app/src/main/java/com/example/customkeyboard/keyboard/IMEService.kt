package com.example.customkeyboard.keyboard

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.customkeyboard.data.KeySize
import com.example.customkeyboard.viewmodel.KeyboardViewModel

class IMEService : LifecycleInputMethodService(),
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    private val store = ViewModelStore()
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry
    override val viewModelStore: ViewModelStore
        get() = store
    override val lifecycle: Lifecycle
        get() = dispatcher.lifecycle
    private lateinit var viewModelKeyboard: KeyboardViewModel

    override fun onCreateInputView(): View {
        val view = ComposeKeyboardView(this, viewModelKeyboard)

        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }
        return view
    }

    override fun onCreate() {
        super.onCreate()
        viewModelKeyboard = ViewModelProvider(this)[KeyboardViewModel::class.java]
        savedStateRegistryController.performRestore(null)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra("FontKey")?.let {
            viewModelKeyboard.setFontKey(it)
        }
        intent?.getStringExtra("ColorKey")?.let {
            viewModelKeyboard.setColorKeys(it)
        }
        intent?.getStringExtra("ColorBackground")?.let {
            viewModelKeyboard.setColorBackground(it)
        }
        intent?.getSerializableExtra("SizeKey", ArrayList::class.java)?.let {
            viewModelKeyboard.setKeySize(
                KeySize(
                    it[0] as Int,
                    it[1] as Int, it[2] as Int, it[3] as Int
                )
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }
}