package com.br.products.presentation.products.view.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.R
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
                placeholder = stringResource(id = R.string.products_search_bar_placeholder),
                onValueChange = {
                },
                onSearchFieldClick = {
                    // TODO: Navigate to search screen
                },
            )
        },
        containerColor = ColorApp.backgroundYellow
    ) { paddingValues ->
        val noBottomPadding = paddingValues.noBottomPadding()
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
private fun PaddingValues.noBottomPadding(): PaddingValues {
    return PaddingValues(
        start = 0.dp,
        top = calculateTopPadding(),
        end = 0.dp,
        bottom = 0.dp
    )
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