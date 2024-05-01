package com.br.design_system.compose.states_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing

/**
 * StateScreen é um componente Composable que exibe diferentes estados de tela com base no estado fornecido.
 * Este componente é geralmente usado para exibir telas de erro, vazio ou sem conexão.
 *
 * @param state O estado da tela a ser exibido. Este é um valor do enum `State`, que inclui `Empty`, `Error` e `NetworkError`.
 * @param onTryAgain Um callback opcional que será chamado quando o botão "Tentar novamente" for clicado. Este botão só é exibido para os estados `Error` e `NetworkError`.
 *
 * Cada estado tem uma imagem e um texto associados, que são exibidos no centro da tela. Se o estado for `Error` ou `NetworkError`, um botão "Tentar novamente" também será exibido.
 * Quando este botão é clicado, o callback `onTryAgain` é chamado.
 */

@Composable
fun StateScreen(state: State, onTryAgain: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .background(ColorApp.background)
            .fillMaxSize()
            .padding(Spacing.scale24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = state.drawableId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(Spacing.scale32))
        Text(
            text = stringResource(id = state.textId),
            color = ColorApp.textOnPrimary,
            textAlign = TextAlign.Center,
            fontSize = FontSize.scaleXs18
        )

        onTryAgain?.let {
            Spacer(modifier = Modifier.height(Spacing.scale24))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonColors(
                    containerColor = ColorApp.primary,
                    contentColor = ColorApp.textOnPrimary,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                onClick = { onTryAgain() }
            ) {
                Text(text = stringResource(id = R.string.try_again_button))
            }
        }
    }
}

enum class State(
    val drawableId: Int,
    val textId: Int,
) {
    Empty(
        drawableId = R.drawable.illu_empty_result,
        textId = R.string.empty_state_title
    ),
    Error(
        drawableId = R.drawable.illu_error,
        textId = R.string.error_state_title,
    ),
    NetworkError(
        drawableId = R.drawable.illu_no_connection,
        textId = R.string.no_connection_state_title,
    )
}

@Composable
@Preview(showSystemUi = true)
private fun StateScreenPreview() {
    MlChallengeTheme {
        StateScreen(State.Error, onTryAgain = {})
    }
}
