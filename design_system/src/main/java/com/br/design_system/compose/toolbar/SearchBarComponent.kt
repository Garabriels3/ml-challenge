package com.br.design_system.compose.toolbar

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import com.br.design_system.compose.outlinedtextfield.CustomOutlinedTextField
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing

@Composable
fun SearchBarComponent(
    value: String,
    placeholder: String?,
    searchButtonState: ImeAction,
    onClickSearchKeyboard: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
    onCancelClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onBackNavigation: (() -> Unit)? = null
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
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Sizing.scale16))
            }
            CustomOutlinedTextField(
                modifier = Modifier.weight(2f),
                value = initialValue,
                keyboardOptions = keyBoardOptions,
                keyboardActions = keyboardActions,
                placeholder = placeholder,
                onValueChange = onValueChange,
                onSearchFieldClick = onSearchFieldClick
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
            onSearchFieldClick = { }
        )
    }
}