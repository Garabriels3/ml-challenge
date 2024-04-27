package com.br.design_system.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.noBottomPadding(): PaddingValues {
    return PaddingValues(
        start = 0.dp,
        top = calculateTopPadding(),
        end = 0.dp,
        bottom = 0.dp
    )
}