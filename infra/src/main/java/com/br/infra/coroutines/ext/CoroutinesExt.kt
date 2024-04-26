package com.br.infra.coroutines.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.br.infra.coroutines.SingleLiveEvent

@Composable
fun <T> SingleLiveEvent<T>.CollectEffect(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    DisposableEffect(key1 = this) {
        val observer = Observer<T> { block(it) }
        this@CollectEffect.observe(lifecycleOwner, observer)
        onDispose {
            this@CollectEffect.removeObserver(observer)
        }
    }
}