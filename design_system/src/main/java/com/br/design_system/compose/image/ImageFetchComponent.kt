package com.br.design_system.compose.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
        contentScale = ContentScale.FillHeight,
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