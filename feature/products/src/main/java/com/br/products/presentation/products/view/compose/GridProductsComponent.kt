package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.Spacing
import com.br.products.presentation.products.udf.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction

@Composable
fun GridProductsComponent(
    triggerAction: (ProductsUiAction) -> Unit,
    pagingProducts: LazyPagingItems<ProductUi>,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = Spacing.scale16)
    ) {
        when (pagingProducts.loadState.append) {
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {

            }

            is LoadState.NotLoading -> {

            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(Spacing.scale16),
            horizontalArrangement = Arrangement.spacedBy(Spacing.scale16),
        ) {
            items(pagingProducts.itemCount) { index ->
                pagingProducts[index]?.let { ProductGridItemComponent(productUi = it) }
            }
        }
    }
}

@Composable
private fun Loading() {
    Text(
        text = "Carregando...",
        fontSize = FontSize.scale3Xl
    )
}