package com.br.products.presentation.mapper

import com.br.products.domain.model.PictureDomain
import com.br.products.domain.model.ProductDetailDomain
import com.br.products.presentation.model.PictureUi
import com.br.products.presentation.model.ProductDetailUi

fun ProductDetailDomain.productDetailDomainToUiModel() = ProductDetailUi(
    id = id,
    title = title,
    pictures = pictures.pictureDomainToUi(),
    price = priceFormatted(),
    permalink = permalink,
    warranty = warranty,
    acceptsMercadoPago = acceptsMercadoPago,
    freeShipping = freeShipping
)

private fun List<PictureDomain>.pictureDomainToUi() = map {
    PictureUi(
        id = it.id,
        url = it.url
    )
}