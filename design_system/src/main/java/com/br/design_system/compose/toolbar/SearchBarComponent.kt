package com.br.design_system.compose.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.compose.outlinedtextfield.CustomOutlinedTextField
import com.br.design_system.theme.BorderRadius
import com.br.design_system.theme.BorderSize
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing

@Composable
fun SearchBarComponent(
    value: String,
    label: String,
    placeholder: String?,
    onValueChange: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
    onBackNavigation: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val initialValue by rememberSaveable(value) { mutableStateOf(value) }

    Box(
        modifier = Modifier.background(color = ColorApp.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = Spacing.scale12)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            onBackNavigation?.let {
                Spacer(modifier = Modifier.width(Sizing.scale24))
                Icon(
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Sizing.scale24))
            }
            CustomOutlinedTextField(
                value = initialValue,
                onValueChange = onValueChange,
                label = label,
                onSearchFieldClick = onSearchFieldClick
            )
            Spacer(modifier = Modifier.width(Sizing.scale24))
        }
    }
}

@Composable
@Preview
private fun SearchBarComponentPreview() {
    MlChallengeTheme {
        SearchBarComponent(
            value = "",
            onValueChange = {},
            label = "Teste",
            placeholder = "Pesquisar",
            onBackNavigation = { },
            onSearchFieldClick = { }
        )
    }
}