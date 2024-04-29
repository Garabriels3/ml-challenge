package com.br.products.domain.usecase.product_detail

import com.br.products.domain.model.ProductDetailDomain
import kotlinx.coroutines.flow.Flow

interface GetProductDetailUseCase {
    operator fun invoke(productId: String): Flow<ProductDetailDomain>
}