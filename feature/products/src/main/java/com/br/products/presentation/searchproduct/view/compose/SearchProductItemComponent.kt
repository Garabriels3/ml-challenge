package com.br.products.presentation.searchproduct.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.R
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing

@Composable
fun SearchProductItemComponent(term: String, onItemClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clickable { onItemClick() }
            .fillMaxWidth()
            .background(color = ColorApp.background)
            .padding(Spacing.scale16),
        verticalAlignment = CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(Sizing.scale20),
            painter = painterResource(id = R.drawable.ic_clock),
            tint = ColorApp.icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Sizing.scale16))
        Text(
            modifier = Modifier.weight(1f),
            text = term,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ColorApp.textOnPrimary,
            fontSize = FontSize.scale2Xs
        )
        Spacer(modifier = Modifier.width(Sizing.scale20))
        Icon(
            modifier = Modifier.size(Sizing.scale16),
            painter = painterResource(id = R.drawable.ic_fetch),
            tint = ColorApp.icon,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun SearchProductItemComponentPreview() {
    MlChallengeTheme {
        SearchProductItemComponent(
            term = "Lorem Impsum dolor sit"
        )
    }
}