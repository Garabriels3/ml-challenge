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

/**
 * LoadingScreen é um componente Composable que exibe um indicador de progresso circular no centro da tela.
 * Este componente é geralmente usado para indicar que algum processo está em andamento e o usuário deve aguardar.
 *
 * O indicador de progresso é centrado horizontal e verticalmente na tela. O fundo da tela é definido para `ColorApp.background`.
 * O tamanho do indicador de progresso é definido para `Sizing.scale120` e a cor para `ColorApp.primary`.
 *
 * Este componente não recebe nenhum parâmetro e não tem estado. Ele apenas exibe um indicador de progresso.
 */

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