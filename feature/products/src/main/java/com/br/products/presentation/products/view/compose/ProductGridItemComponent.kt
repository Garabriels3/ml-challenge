package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.compose.image.ImageFetchComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.br.design_system.theme.Spacing
import com.br.infra.coroutines.ext.toHttpsUri
import com.br.products.presentation.products.udf.ProductUi

@Composable
fun ProductGridItemComponent(productUi: ProductUi) {
    Box(
        modifier = Modifier.width(IntrinsicSize.Min).background(ColorApp.surface)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.scale16),
        ) {
            ImageFetchComponent(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                imageUrl = productUi.imageUrl.toHttpsUri()
            )
            Spacer(modifier = Modifier.height(Sizing.scale20))
            if (!productUi.freeShipping.isNullOrEmpty()) {
                Text(
                    text = productUi.freeShipping,
                    textAlign = TextAlign.Start,
                    fontSize = FontSize.scale3Xs,
                    color = ColorApp.textGreen,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = productUi.name,
                textAlign = TextAlign.Start,
                fontSize = FontSize.scale2Xs,
                color = ColorApp.textOnSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatCurrency(productUi.price),
                textAlign = TextAlign.Start,
                fontSize = FontSize.scaleXs,
                color = ColorApp.textOnSurface
            )
            Text(
                text = "${productUi.availableQuantity} restantes",
                textAlign = TextAlign.Start,
                fontSize = FontSize.scale3Xs,
                color = ColorApp.textOnPrimary
            )
            Text(
                text = productUi.condition,
                fontSize = FontSize.scale3Xs,
                color = ColorApp.textOnSurface
            )
        }
    }
}

private fun formatCurrency(value: Double): String {
    return "R$ $value.00"
}

@Composable
@Preview(showSystemUi = false, showBackground = true)
private fun ProductGridItemComponentPreview() {
    MlChallengeTheme {
        ProductGridItemComponent(
            ProductUi(
                id = "1",
                name = "Placa de Video Nvidia RTX 3080 10GB GDDR6X GDDR6X",
                price = 100.0,
                imageUrl = "http://http2.mlstatic.com/D_733881-MLA44173736562_112020-I.jpg",
                availableQuantity = 10,
                freeShipping = "Frete grátis",
                condition = "Novo"
            )
        )
    }
}