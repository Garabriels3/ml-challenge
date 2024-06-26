package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.design_system.compose.image.ImageFetchComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing
import com.br.infra.coroutines.ext.toHttpsUri
import com.br.products.R
import com.br.products.presentation.model.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction

@Composable
fun ProductListItemComponent(productUi: ProductUi, onItemClick: (ProductsUiAction) -> Unit = {}) {
    val freeShippingMessage = productUi.freeShipping?.let { stringResource(id = it) }
    Box(
        modifier = Modifier
            .clickable {
                onItemClick(ProductsUiAction.OnProductClickAction(productId = productUi.id))
            }
            .fillMaxWidth()
            .background(ColorApp.surface)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.scale16),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageFetchComponent(
                modifier = Modifier
                    .size(72.dp)
                    .padding(end = Spacing.scale8),
                imageUrl = productUi.imageUrl.toHttpsUri(),
            )
            Column(modifier = Modifier.weight(3f)) {
                if (!freeShippingMessage.isNullOrEmpty()) {
                    Text(
                        text = freeShippingMessage,
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
                    text = productUi.price,
                    textAlign = TextAlign.Start,
                    fontSize = FontSize.scaleXs,
                    color = ColorApp.textOnSurface
                )
            }
            Column(modifier = Modifier.align(Alignment.Bottom)) { // Align content at the bottom
                Text(
                    text = "${productUi.availableQuantity} restantes",
                    textAlign = TextAlign.Start,
                    fontSize = FontSize.scale3Xs,
                    color = ColorApp.textOnPrimary
                )
                Text(
                    text = productUi.condition,
                    fontSize = FontSize.scale4Xs,
                    color = ColorApp.textOnSurface
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = false, showBackground = true)
private fun ProductItemComponentPreview() {
    MlChallengeTheme {
        ProductListItemComponent(
            ProductUi(
                id = "1",
                name = "Placa de Video Nvidia RTX 3080 10GB GDDR6X GDDR6X",
                price = "R$ 1000,00",
                imageUrl = "http://http2.mlstatic.com/D_733881-MLA44173736562_112020-I.jpg",
                condition = "Novo",
                availableQuantity = 10,
                freeShipping = R.string.products_free_shipping_label,
            )
        )
    }
}
