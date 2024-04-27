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
            placeholder = "Pesquisar",
            onValueChange = {},
        )
    }
}