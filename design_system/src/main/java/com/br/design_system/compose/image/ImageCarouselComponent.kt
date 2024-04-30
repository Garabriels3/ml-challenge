package com.br.design_system.compose.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.theme.ColorApp
import com.br.design_system.theme.MlChallengeTheme
import com.br.design_system.theme.Sizing
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

/**
 * ImageCarouselComponent é um componente Composable que exibe um carrossel de imagens.
 *
 * @param imageUrls Uma lista de URLs de imagens a serem exibidas no carrossel.
 *
 * Este componente usa o `HorizontalPager` do Accompanist para exibir um carrossel de imagens. Cada página do carrossel exibe uma imagem da lista `imageUrls`.
 * A imagem é carregada e exibida usando o componente `ImageFetchComponent`.
 *
 * O estado do carrossel é mantido usando `rememberPagerState`, que mantém o estado atual da página e o total de páginas.
 *
 * Abaixo do carrossel, um `HorizontalPagerIndicator` é exibido, que mostra um indicador para cada página do carrossel. O indicador da página atual é destacado.
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageCarouselComponent(imageUrls: List<String>) {
    val pagerState = rememberPagerState(pageCount = imageUrls.size)

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            Box {
                ImageFetchComponent(
                    modifier = Modifier.aspectRatio(1f),
                    imageUrl = imageUrls[page]
                )
            }
        }
        Spacer(modifier = Modifier.height(Sizing.scale12))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier,
            activeColor = ColorApp.primary
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ImageCarouselPreview() {
    val imageUrls = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg"
    )

    MlChallengeTheme {
        ImageCarouselComponent(imageUrls = imageUrls)
    }
}