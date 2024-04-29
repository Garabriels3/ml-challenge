package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.br.design_system.compose.states_screen.State
import com.br.design_system.compose.states_screen.StateScreen
import com.br.design_system.theme.Spacing
import com.br.products.presentation.model.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction

@Composable
fun ListProductsComponent(
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
        if (pagingProducts.isPagingEmptyState()) {
            StateScreen(State.Empty)
        } else {
            LazyColumn {
                items(pagingProducts.itemCount) { index ->
                    pagingProducts[index]?.let {
                        ProductListItemComponent(
                            productUi = it,
                            onItemClick = triggerAction
                        )
                    }
                }
            }
        }
    }
}

private fun LazyPagingItems<ProductUi>.isPagingEmptyState() =
    loadState.refresh is LoadState.NotLoading && itemCount == 0