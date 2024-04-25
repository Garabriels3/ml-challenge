package com.br.infra.coroutines

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer

//@Composable
//fun <T> SingleLiveEvent<T>.collectEffect(block: (T) -> Unit) {
//    val lifecycle = LocalLifecycleOwner.current
//    DisposableEffect(key1 = this) {
//        val observer = Observer<T> { block(it) }
//        this@collectEffect.observe(lifecycle, observer)
//        onDispose {
//            this@collectEffect.removeObserver(observer)
//        }
//    }
//}