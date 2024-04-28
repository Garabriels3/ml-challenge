package com.br.products.domain.usecase.product_detail

import com.br.products.domain.model.ProductDetailDomain
import com.br.products.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow

class ProductDetailUseCaseImpl(
    private val productDetailRepository: ProductDetailRepository
) : ProductDetailUseCase {
    override fun getProductDetail(productId: String): Flow<ProductDetailDomain> {
        return productDetailRepository.getProductDetail(productId)
    }
}