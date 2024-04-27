package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.br.products.presentation.products.udf.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction

@Composable
fun ListProductsComponent(
    triggerAction: (ProductsUiAction) -> Unit,
    pagingProducts: LazyPagingItems<ProductUi>,
    paddingValues: PaddingValues
) {
    Column {
        LazyColumn {
            items(pagingProducts.itemCount) { index ->
                pagingProducts[index]?.let { ProductListItemComponent(productUi = it) }
            }
        }
    }
}