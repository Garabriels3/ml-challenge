package com.br.design_system.compose.outlinedtextfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.BorderRadius
import com.br.design_system.theme.MlChallengeTheme

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
    label: String,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.clickable {
            onSearchFieldClick.invoke()
        },
        onValueChange = onValueChange,
        shape = RoundedCornerShape(BorderRadius.lg),
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
    )
}

@Composable
@Preview(showBackground = true)
fun CustomOutlinedTextFieldPreview() {
    MlChallengeTheme {
        CustomOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Teste",
            placeholder = "Pesquisar",
            onSearchFieldClick = { }
        )
    }
}