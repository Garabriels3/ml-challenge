package com.br.design_system.compose.states_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing

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
                onClick = { onTryAgain() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
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
        drawableId = R.drawable.illu_no_connection,
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
