package com.br.design_system.compose.toolbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme

@Composable
fun SearchBarComponent(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String?,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val initialValue by rememberSaveable(value) { mutableStateOf(value) }

    Box {
        Row {
            SearchField(
                value = initialValue,
                onValueChange = onValueChange,
                label = label
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = singleLine,
        label = { Text(text = label) },
        placeholder = placeholder?.let { { Text(text = it) } },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconContentDescription
                )
            }
        },
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = ColorApp.background,
            cursorColor = ColorApp.textOnPrimary
        )
    )
}

@Composable
@Preview
private fun SearchBarComponentPreview() {
    MlChallengeTheme {
        SearchBarComponent(
            value = "",
            onValueChange = {},
            label = "Teste",
            placeholder = "Pesquisar"
        )
    }
}