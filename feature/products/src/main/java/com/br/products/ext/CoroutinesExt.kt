package com.br.products.ext

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

// Precisei colocar essa Extension aqui por motivos de erro de compilação
// Por alguma motivo que não consegui entender o projeto não estava idendificando a existencia dessa extension
// precisei colocar ela no modulo de feature mas o correto seria deixar no meu modulo de infra