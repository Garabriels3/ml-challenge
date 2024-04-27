package com.br.design_system.compose.states_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorApp.background),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Sizing.scale120),
            color = ColorApp.primary
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun LoadingScreenPreview() {
    MlChallengeTheme {
        LoadingScreen()
    }
}