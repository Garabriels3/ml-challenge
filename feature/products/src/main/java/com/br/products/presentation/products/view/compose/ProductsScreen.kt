package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.FontSize
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing
import com.br.products.presentation.products.udf.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.udf.ProductsUiModel
import com.br.products.presentation.products.udf.ProductsUiState

@Composable
fun ProductsScreen(
    state: ProductsUiState,
    triggerAction: (ProductsUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchBarComponent(
                value = state.uiModel.searchedTerm,
                placeholder = "Buscar",
                onValueChange = {
                },
                onSearchFieldClick = {
                    // TODO: Navigate to search screen
                },
            )
        },
        containerColor = ColorApp.backgroundYellow
    ) { paddingValues ->
        val noBottomPadding = PaddingValues(
            start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
            top = paddingValues.calculateTopPadding(),
            end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
            bottom = 0.dp
        )
        val products = state.uiModel.products.collectAsLazyPagingItems()
        when (state) {
            is ProductsUiState.OnResumedGridState -> {
                GridProductsComponent(
                    paddingValues = noBottomPadding,
                    pagingProducts = products,
                    triggerAction = triggerAction
                )
            }

            is ProductsUiState.OnResumedListState -> {
                ListProductsComponent(
                    paddingValues = noBottomPadding,
                    pagingProducts = products,
                    triggerAction = triggerAction
                )
            }

            is ProductsUiState.OnLoadingState -> {
            }

            is ProductsUiState.OnErrorState -> {
            }

            is ProductsUiState.OnEmptyState -> {
            }
        }
    }
}

@Composable
private fun ObservePagingState(products: LazyPagingItems<ProductUi>) {
    when (products.loadState.append) {
        is LoadState.Loading -> {
            Loading() // Ajeitar posicao
        }

        is LoadState.Error -> {

        }

        is LoadState.NotLoading -> {

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

@Composable
private fun GridProductsComponent(
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
        ObservePagingState(pagingProducts)
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
private fun ListProductsComponent(
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

@Preview
@Composable
private fun ProductsScreenPreview() {
    MlChallengeTheme {
        ProductsScreen(
            state = ProductsUiState.OnResumedGridState(
                uiModel = ProductsUiModel()
            ),
            triggerAction = {},
        )
    }
}