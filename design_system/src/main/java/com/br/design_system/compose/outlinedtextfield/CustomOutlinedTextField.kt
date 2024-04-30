package com.br.design_system.compose.outlinedtextfield

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.BorderRadius
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.Elevation
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme

/**
 * CustomOutlinedTextField é um componente de campo de texto personalizado com contorno.
 *
 * @param modifier Modificador Compose para aplicar ao campo de texto.
 * @param value O valor atual do campo de texto.
 * @param label O texto do rótulo a ser exibido quando não há texto no campo.
 * @param placeholder O texto do placeholder a ser exibido no campo de texto quando não há foco e o campo está vazio.
 * @param keyboardOptions Opções de configuração do teclado, como tipo de teclado e correção automática.
 * @param keyboardActions Ações do teclado, como a ação do botão Enter.
 * @param singleLine Se verdadeiro, o campo de texto será de uma única linha. Caso contrário, será de várias linhas.
 * @param readOnly Se verdadeiro, o campo de texto será somente leitura.
 * @param leadingIcon Um ícone a ser exibido antes do campo de texto.
 * @param leadingIconContentDescription A descrição do conteúdo do ícone principal. Isso será anunciado para leitores de tela.
 * @param onValueChange Uma função a ser chamada sempre que o valor do campo de texto mudar.
 */

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier,
    value: String,
    label: String? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        readOnly = readOnly,
        enabled = !readOnly,
        modifier = modifier
            .shadow(
                elevation = Elevation.low,
                shape = RoundedCornerShape(BorderRadius.lg),
                clip = true,
                spotColor = ColorApp.outline
            ),
        onValueChange = onValueChange,
        shape = RoundedCornerShape(BorderRadius.lg),
        singleLine = singleLine,
        label = label?.let {
            {
                Text(
                    text = it,
                    fontSize = FontSize.scaleXs
                )
            }
        },
        placeholder = placeholder?.let {
            {
                Text(
                    text = it,
                    fontSize = FontSize.scale3Xs
                )
            }
        },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconContentDescription
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = ColorApp.background,
            disabledContainerColor = ColorApp.background,
            disabledPlaceholderColor = ColorApp.textOnSurface,
            disabledTextColor = ColorApp.textOnSurface,
            focusedContainerColor = ColorApp.background,
            focusedBorderColor = ColorApp.outline,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}

@Composable
@Preview(showBackground = true)
fun CustomOutlinedTextFieldPreview() {
    MlChallengeTheme {
        CustomOutlinedTextField(
            modifier = Modifier,
            value = "",
            readOnly = true,
            placeholder = "Pesquisar",
            onValueChange = {},
        )
    }
}