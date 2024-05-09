package com.br.products.presentation.products.view.compose

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.br.design_system.compose.noBottomPadding
import com.br.design_system.compose.states_screen.LoadingScreen
import com.br.design_system.compose.states_screen.State
import com.br.design_system.compose.states_screen.StateScreen
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.infra.coroutines.SingleLiveEvent
import com.br.infra.coroutines.ext.CollectEffect
import com.br.products.R
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.udf.ProductsUiModel
import com.br.products.presentation.products.udf.ProductsUiSideEffect
import com.br.products.presentation.products.udf.ProductsUiState

@Composable
fun ProductsScreen(
    state: ProductsUiState,
    effect: SingleLiveEvent<ProductsUiSideEffect> = SingleLiveEvent(),
    navController: NavController? = null,
    triggerAction: (ProductsUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchBarComponent(
                value = state.uiModel.searchedTerm,
                readOnly = true,
                placeholder = stringResource(id = R.string.products_search_bar_placeholder),
                suffixIcon = state.uiModel.productsOrientation.value,
                onBackNavigation = {
                    triggerAction(ProductsUiAction.OnClickSearchBarAction)
                },
                onClickSuffixIcon = {
                    triggerAction(ProductsUiAction.OnProductsOrientationClickAction(state.uiModel.productsOrientation))
                },
            )
        },
        containerColor = ColorApp.backgroundYellow
    ) { paddingValues ->
        val noBottomPadding = paddingValues.noBottomPadding()
        val products = state.uiModel.products.collectAsLazyPagingItems()
        ObserveUiEffects(effect, navController = navController)
        Column(modifier = Modifier.fillMaxSize()) {
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
                    LoadingScreen()
                }

                is ProductsUiState.OnErrorState -> {
                    StateScreen(state = State.Error) {
                        triggerAction(ProductsUiAction.OnRetryAction)
                    }
                }

                is ProductsUiState.OnNetworkErrorState -> {
                    StateScreen(state = State.NetworkError)
                }
            }
        }
    }
}

@Composable
private fun ObserveUiEffects(
    effect: SingleLiveEvent<ProductsUiSideEffect>,
    navController: NavController?,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    effect.CollectEffect(lifecycleOwner) {
        when (it) {
            is ProductsUiSideEffect.OnBackToSearchEffect -> {
                navController?.popBackStack()
            }

            is ProductsUiSideEffect.OnNavigateToDetailEffect -> {
                navController?.navigate(
                    R.id.action_productsFragment_to_productDetailFragment,
                    Bundle().apply { putString("productId", it.productId) }
                )
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