package com.br.products.presentation.productdetail.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.br.design_system.compose.image.ImageCarouselComponent
import com.br.design_system.compose.noBottomPadding
import com.br.design_system.compose.states_screen.LoadingScreen
import com.br.design_system.compose.states_screen.State
import com.br.design_system.compose.states_screen.StateScreen
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Spacing
import com.br.infra.coroutines.SingleLiveEvent
import com.br.infra.coroutines.ext.CollectEffect
import com.br.navigation.extension.openUrlInBrowser
import com.br.products.R
import com.br.products.presentation.model.PictureUi
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

            is ProductDetailUiState.OnNetworkErrorState -> {
                StateScreen(state = State.NetworkError) {
                    triggerAction(ProductDetailUiAction.OnClickTryAgain(state.uiModel.productId))
                }
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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues.noBottomPadding())
            .padding(horizontal = Spacing.scale16)
            .testTag("ProductDetailContent")
    ) {
        ImageCarouselComponent(imageUrls = product.pictures.map { it.url })
        Spacer(modifier = Modifier.height(Spacing.scale24))
        ProductDetailHeader(product)
        Spacer(modifier = Modifier.height(Spacing.scale16))
        SeeAttributesButton {
            triggerAction(ProductDetailUiAction.OnClickToBuy(product.permalink))
        }
    }
}

@Composable
private fun ProductDetailHeader(product: ProductDetailUi) {
    Text(
        text = product.title,
        maxLines = 3,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = product.price,
        style = MaterialTheme.typography.displaySmall
    )
    product.warranty?.let {
        Text(
            text = product.warranty,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    if (product.acceptsMercadoPago) {
        Text(
            text = stringResource(id = R.string.product_detail_pay_marketing_label),
            style = MaterialTheme.typography.labelLarge,
            color = ColorApp.blueMercadoPago
        )
    }
}

@Composable
private fun SeeAttributesButton(
    onClickToBuy: () -> Unit = {}
) {
    Row {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonColors(
                containerColor = ColorApp.primary,
                contentColor = ColorApp.textOnPrimary,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray
            ),
            onClick = { onClickToBuy() }
        ) {
            Text(text = stringResource(id = R.string.product_detail_buy_now_label))
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
                navController?.openUrlInBrowser(it.url)
            }

            is ProductDetailUiSideEffect.OnNavigateToSearchScreen -> {
                navController?.navigate(R.id.action_productsDetail_to_searchProductFragment)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, device = "id:Nexus One")
@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_2")
@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_4a")
private fun ProductDetailScreenPreview() {
    MlChallengeTheme {
        ProductDetailScreen(
            state = ProductDetailUiState.OnResumedState(
                ProductDetailUiModel(
                    productDetail = ProductDetailUi(
                        id = "1",
                        title = "Placa de Vídeo Nvidia RTX 3080 Ti 12GB GDDR6X 384 bits",
                        pictures = listOf(PictureUi("1", "https://example.com/image1.jpg")),
                        price = "R$ 100,00",
                        warranty = "Garantia do vendedor: 2 anos",
                        acceptsMercadoPago = true,
                        freeShipping = true,
                        permalink = "https://example.com/product/1"
                    )
                )
            ),
            triggerAction = {}
        )
    }
}