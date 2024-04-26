package com.br.products.presentation.searchproduct.view.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.br.design_system.compose.snackbar.SnackBarNotifier
import com.br.design_system.compose.snackbar.SnackbarStatus
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing
import com.br.infra.connectionchecker.isNetworkAvailable
import com.br.infra.coroutines.SingleLiveEvent
import com.br.infra.coroutines.ext.CollectEffect
import com.br.products.presentation.searchproduct.udf.SearchProductUiAction
import com.br.products.presentation.searchproduct.udf.SearchProductUiModel
import com.br.products.presentation.searchproduct.udf.SearchProductUiSideEffect
import com.br.products.presentation.searchproduct.udf.SearchProductUiState
import kotlinx.coroutines.launch

@Composable
fun SearchProductScreen(
    state: SearchProductUiState,
    effect: SingleLiveEvent<SearchProductUiSideEffect> = SingleLiveEvent(),
    navController: NavController? = null,
    triggerAction: (SearchProductUiAction) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    ObserveUiEffects(effect, snackBarHostState)
    when (state) {
        is SearchProductUiState.OnResumeState -> {
            SearchProductContent(
                state.uiModel,
                snackBarHostState,
                triggerAction
            )
        }

        is SearchProductUiState.OnLoadingState -> {
        }

        is SearchProductUiState.OnEmptyState -> {
        }

        is SearchProductUiState.OnErrorState -> {
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ObserveUiEffects(
    effect: SingleLiveEvent<SearchProductUiSideEffect>,
    snackBarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    effect.CollectEffect(lifecycleOwner) {
        when (it) {
            is SearchProductUiSideEffect.OnNavigateToProductDetailsEffect -> {
                // navController.navigate("products_nav_graph")
            }

            is SearchProductUiSideEffect.OnShowToastEffect -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = it.message,
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchProductContent(
    uiModel: SearchProductUiModel,
    snackBarHostState: SnackbarHostState,
    triggerAction: (SearchProductUiAction) -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            Box {
                SnackBarNotifier(
                    modifier = Modifier.padding(Spacing.scale16),
                    snackBarHostState = snackBarHostState,
                    status = SnackbarStatus.ERROR
                )
            }
        },
        topBar = {
            SearchBarComponent(
                value = uiModel.productName,
                placeholder = "Buscar",
                onValueChange = {
                    triggerAction(SearchProductUiAction.OnTextChangedAction(it))
                },
                searchButtonState = uiModel.isSearchButtonEnabled,
                onSearchFieldClick = {},
                onCancelClick = {
                    triggerAction(SearchProductUiAction.OnCancelSearchAction)
                },
                onClickSearchKeyboard = {
                    triggerAction(
                        SearchProductUiAction.OnClickSearchAction(
                            it,
                            context.isNetworkAvailable()
                        )
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(uiModel.productsHistory) { product ->
                SearchProductItemComponent(product)
            }
        }
    }
}

@Preview
@Composable
private fun SearchProductScreenPreview() {
    MlChallengeTheme {
        SearchProductScreen(
            SearchProductUiState.OnResumeState(
                SearchProductUiModel(
                    productName = "Product",
                    isSearchButtonEnabled = ImeAction.Search,
                    productsHistory = listOf("Product 1", "Product 2")
                )
            ),
        ) {}
    }
}