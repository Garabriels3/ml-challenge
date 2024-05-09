package com.br.design_system.compose.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * ImageFetchComponent é um componente Composable que carrega e exibe uma imagem a partir de uma URL.
 *
 * @param modifier O modificador a ser aplicado ao componente.
 * @param imageUrl A URL da imagem a ser carregada e exibida.
 * @param placeholder O recurso de imagem a ser exibido enquanto a imagem está sendo carregada.
 *
 * Este componente usa o `AsyncImage` do Coil para carregar a imagem. A imagem é carregada de forma assíncrona e exibida quando pronta.
 * Enquanto a imagem está sendo carregada, o `placeholder` é exibido.
 * O `ImageRequest.Builder` é usado para construir a solicitação de imagem, com `crossfade` definido como verdadeiro para uma transição suave quando a imagem é carregada.
 */

@Composable
fun ImageFetchComponent(
    modifier: Modifier,
    imageUrl: String,
    placeholder: Int = android.R.drawable.ic_menu_report_image
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(id = placeholder),
        contentScale = ContentScale.Fit,
    )
}

@Preview
@Composable
private fun ImageFetchComponentPreview() {
    ImageFetchComponent(
        modifier = Modifier,
        placeholder = android.R.drawable.ic_menu_report_image,
        imageUrl = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLB46028200085_052021-F.webp"
    )
}