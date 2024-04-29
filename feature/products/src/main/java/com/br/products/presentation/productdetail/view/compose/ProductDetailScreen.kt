package com.br.products.presentation.productdetail.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.br.design_system.compose.states_screen.LoadingScreen
import com.br.design_system.compose.states_screen.State
import com.br.design_system.compose.states_screen.StateScreen
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing
import com.br.infra.coroutines.SingleLiveEvent
import com.br.infra.coroutines.ext.CollectEffect
import com.br.products.R
import com.br.products.presentation.model.AttributeUi
import com.br.products.presentation.model.ProductDetailUi
import com.br.products.presentation.productdetail.udf.ProductDetailUiAction
import com.br.products.presentation.productdetail.udf.ProductDetailUiModel
import com.br.products.presentation.productdetail.udf.ProductDetailUiSideEffect
import com.br.products.presentation.productdetail.udf.ProductDetailUiState

@Composable
fun ProductDetailScreen(
    state: ProductDetailUiState,
    effect: SingleLiveEvent<ProductDetailUiSideEffect> = SingleLiveEvent(),
    navController: NavController? = null,
    triggerAction: (ProductDetailUiAction) -> Unit
) {
    ObserverUiEffects(effect, navController = navController)
    Scaffold(
        topBar = {
            SearchBarComponent(
                placeholder = stringResource(id = R.string.products_search_bar_placeholder),
                onBackNavigation = { triggerAction(ProductDetailUiAction.OnClickNavigateBack) },
                onClickSearchField = { triggerAction(ProductDetailUiAction.OnClickNavigateToSearchScreen) },
                readOnly = true
            )
        }
    ) {
        when (state) {
            is ProductDetailUiState.OnLoadingState -> {
                LoadingScreen()
            }

            is ProductDetailUiState.OnErrorState -> {
                StateScreen(state = State.Error) {
                    triggerAction(ProductDetailUiAction.OnClickTryAgain(state.uiModel.productId))
                }
            }

            is ProductDetailUiState.OnResumedState -> {
                ProductDetailContent(state.uiModel.productDetail, it, triggerAction)
            }

            is ProductDetailUiState.OnNetworkState -> {
                StateScreen(state = State.NetworkError) {
                    triggerAction(ProductDetailUiAction.OnClickTryAgain(state.uiModel.productId))
                }
            }
        }
    }
}

@Composable
private fun ObserverUiEffects(
    effect: SingleLiveEvent<ProductDetailUiSideEffect>,
    navController: NavController? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    effect.CollectEffect(lifecycleOwner = lifecycleOwner) {
        when (it) {
            is ProductDetailUiSideEffect.OnNavigateBack -> {
                navController?.popBackStack()
            }

            is ProductDetailUiSideEffect.OnNavigateToBrowser -> {

            }

            is ProductDetailUiSideEffect.OnNavigateToSearchScreen -> {
                navController?.navigate(R.id.action_searchProductFragment_to_productsFragment)
            }
        }
    }
}

@Composable
private fun ProductDetailContent(
    product: ProductDetailUi,
    paddingValues: PaddingValues,
    triggerAction: (ProductDetailUiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(Spacing.scale16)
    ) {
        Text(text = product.title, style = MaterialTheme.typography.titleMedium)
        Text(text = product.price, style = MaterialTheme.typography.titleMedium)
        Button(onClick = { /*TODO: Handle click*/ }) {
            Text(text = "Ver atributos")
        }
        // TODO: Add more product details
    }
}

@Composable
@Preview(showSystemUi = true)
private fun ProductDetailScreenPreview() {
    MlChallengeTheme {
        ProductDetailScreen(
            state = ProductDetailUiState.OnResumedState(
                ProductDetailUiModel(
                    productDetail = ProductDetailUi(
                        title = "Product title",
                        attributes = listOf(
                            AttributeUi("1", "Attribute 1", "Description 1"),
                            AttributeUi("1", "Attribute 1", "Description 1"),
                            AttributeUi("1", "Attribute 1", "Description 1")
                        ),
                        price = "R$ 100,00",
                        id = "1",
                        pictures = emptyList(),
                        warranty = "Garantia do vendedor: 2 anos",
                        acceptsMercadoPago = true,
                        freeShipping = true
                    )
                )
            ),
            triggerAction = {}
        )
    }
}