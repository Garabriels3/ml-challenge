package com.br.design_system.compose.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme

/**
 * SnackBarNotifier é um componente que exibe uma Snackbar com uma mensagem para o usuário.
 *
 * @param snackBarHostState O estado do host da Snackbar. Este estado controla a exibição da Snackbar.
 * @param status O status da Snackbar, que determina as cores de fundo e do texto da Snackbar.
 * @param modifier Modificador Compose para aplicar à Snackbar.
 *
 * Este componente usa o `SnackbarHost` do Material-UI Compose para exibir a Snackbar. A Snackbar é exibida
 * com uma cor de fundo e de texto que correspondem ao `status` fornecido.
 *
 * O `snackBarHostState` é usado para controlar a exibição da Snackbar. Você pode usar a função `showSnackbar` do
 * `snackBarHostState` para exibir uma Snackbar com uma mensagem.
 */

@Composable
fun SnackBarNotifier(
    snackBarHostState: SnackbarHostState,
    status: SnackbarStatus,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier,
        snackbar = { snackBarData ->
            Snackbar(
                contentColor = status.textColor,
                containerColor = status.background,
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = snackBarData.visuals.message,
                            color = status.textColor
                        )
                    }
                }
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun SnackBarNotifierPreview() {
    val snackBarHostState = remember { SnackbarHostState() }

    MlChallengeTheme {
        SnackBarNotifier(
            snackBarHostState = snackBarHostState,
            status = SnackbarStatus.WARNING
        )

        LaunchedEffect(key1 = snackBarHostState) {
            snackBarHostState.showSnackbar(
                message = "Você esta sem conexão com a internet!",
            )
        }
    }
}

enum class SnackbarStatus(val background: Color, val textColor: Color) {
    SUCCESS(ColorApp.success, ColorApp.textOnSurfaceDark),
    WARNING(ColorApp.primary, ColorApp.textOnPrimary),
    ERROR(ColorApp.error, ColorApp.textOnSurfaceDark),
}
