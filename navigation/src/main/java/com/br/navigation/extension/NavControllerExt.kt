package com.br.navigation.extension

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController

fun NavController.openUrlInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.context.startActivity(intent)
}