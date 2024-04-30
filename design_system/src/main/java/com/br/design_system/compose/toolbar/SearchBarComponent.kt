package com.br.design_system.compose.toolbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.compose.outlinedtextfield.CustomOutlinedTextField
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing

/**
 * SearchBarComponent é um componente Composable que exibe uma barra de pesquisa.
 *
 * @param value O valor atual do campo de pesquisa.
 * @param placeholder O texto do placeholder a ser exibido no campo de pesquisa quando não há foco e o campo está vazio.
 * @param readOnly Se verdadeiro, o campo de pesquisa será somente leitura.
 * @param searchButtonState O estado do botão de pesquisa no teclado. Este é um valor do enum `ImeAction`.
 * @param onClickSearchKeyboard Um callback que será chamado quando o botão de pesquisa no teclado for clicado.
 * @param onCancelClick Um callback opcional que será chamado quando o botão "Cancelar" for clicado. Este botão só é exibido quando o campo de pesquisa não está vazio.
 * @param onValueChange Uma função a ser chamada sempre que o valor do campo de pesquisa mudar.
 * @param onClickSearchField Um callback que será chamado quando o campo de pesquisa for clicado.
 * @param onBackNavigation Um callback opcional que será chamado quando o botão de navegação para trás for clicado.
 *
 * Este componente exibe um campo de pesquisa com um botão de navegação para trás opcional e um botão "Cancelar" opcional.
 * O botão de navegação para trás é exibido se o callback `onBackNavigation` for fornecido.
 * O botão "Cancelar" é exibido se o callback `onCancelClick` for fornecido e o campo de pesquisa não está vazio.
 * Quando o botão de pesquisa no teclado é clicado, o callback `onClickSearchKeyboard` é chamado.
 * Quando o campo de pesquisa é clicado, o callback `onClickSearchField` é chamado.
 * Quando o valor do campo de pesquisa muda, a função `onValueChange` é chamada.
 */

@Composable
fun SearchBarComponent(
    value: String = "",
    placeholder: String?,
    readOnly: Boolean = false,
    searchButtonState: ImeAction = ImeAction.None,
    onClickSearchKeyboard: ((String) -> Unit) = {},
    onCancelClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    onClickSearchField: (() -> Unit) = {},
    onBackNavigation: (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val initialValue by rememberSaveable(value) { mutableStateOf(value) }
    val keyBoardOptions = KeyboardOptions(
        imeAction = searchButtonState
    )
    val keyboardActions = KeyboardActions(
        onSearch = {
            keyboardController?.hide()
            onClickSearchKeyboard(initialValue)
        }
    )

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
            onBackNavigation?.let {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onBackNavigation() }
                )
                Spacer(modifier = Modifier.width(Sizing.scale16))
            }
            CustomOutlinedTextField(
                modifier = Modifier
                    .clickable { onClickSearchField.invoke() }
                    .weight(2f),
                value = initialValue,
                readOnly = readOnly,
                keyboardOptions = keyBoardOptions,
                keyboardActions = keyboardActions,
                placeholder = placeholder,
                onValueChange = onValueChange,
            )
            if (onCancelClick != null && value.isNotEmpty()) {
                TextButton(
                    modifier = Modifier.weight(0.7f),
                    onClick = { onCancelClick() }
                ) {
                    Text(
                        text = "Cancelar",
                        color = ColorApp.textOnPrimary
                    )
                }
            }
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true, showBackground = true
)
private fun SearchBarComponentPreview() {
    MlChallengeTheme {
        SearchBarComponent(
            value = "",
            onValueChange = {},
            placeholder = "Pesquisar",
            searchButtonState = ImeAction.Search,
            onCancelClick = { },
            onBackNavigation = { },
            onClickSearchKeyboard = { },
        )
    }
}