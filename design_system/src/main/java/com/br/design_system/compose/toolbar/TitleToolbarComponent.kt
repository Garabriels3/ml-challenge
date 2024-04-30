package com.br.design_system.compose.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing

/**
 * TitleToolbarComponent é um componente Composable que exibe uma barra de ferramentas com um título e um botão de navegação para trás.
 *
 * @param title O título a ser exibido na barra de ferramentas.
 * @param onBackNavigation Um callback que será chamado quando o botão de navegação para trás for clicado.
 *
 * Este componente exibe uma barra de ferramentas com um título e um botão de navegação para trás. O título é exibido com um overflow de texto para elipses se for muito longo para caber na tela.
 * O botão de navegação para trás é exibido à esquerda do título. Quando este botão é clicado, o callback `onBackNavigation` é chamado.
 */

@Composable
fun TitleToolbarComponent(
    title: String,
    onBackNavigation: (() -> Unit),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorApp.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(Spacing.scale12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onBackNavigation() }
            )
            Spacer(modifier = Modifier.width(Sizing.scale24))
            Text(
                text = title, color = ColorApp.textOnPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview
private fun TitleToolbarComponentPreview() {
    MlChallengeTheme {
        TitleToolbarComponent(
            title = "Motorola G100 - 128GB - 8GB RAM - 5G - 64MP",
        ) {}
    }
}