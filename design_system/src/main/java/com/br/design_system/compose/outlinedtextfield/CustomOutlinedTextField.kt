package com.br.design_system.compose.outlinedtextfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.br.design_system.theme.Sizing

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier,
    value: String,
    label: String? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null,
    onValueChange: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = modifier
            .shadow(
                elevation = Elevation.low,
                shape = RoundedCornerShape(BorderRadius.lg),
                clip = true,
                spotColor = ColorApp.outline
            )
            .clickable {
                onSearchFieldClick.invoke()
            },
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
                    fontSize = FontSize.scale4Xs
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
            onSearchFieldClick = { },
        )
    }
}