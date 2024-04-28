package com.br.products.data.repository

import com.br.products.data.datasource.remote.product_detail.ProductDetailRemoteDataSource
import com.br.products.data.model.response.AttributeResponse
import com.br.products.data.model.response.PictureResponse
import com.br.products.data.model.response.ProductDetailResponse
import com.br.products.domain.model.AttributeDomain
import com.br.products.domain.model.PictureDomain
import com.br.products.domain.model.ProductDetailDomain
import com.br.products.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductDetailRepositoryImpl(
    private val productDetailRemoteDataSource: ProductDetailRemoteDataSource
) : ProductDetailRepository {
    override fun getProductDetail(productId: String): Flow<ProductDetailDomain> {
        return productDetailRemoteDataSource.getProductDetail(productId).map { response ->
            response.toDomain()
        }
    }

    private fun ProductDetailResponse.toDomain(): ProductDetailDomain {
        return ProductDetailDomain(
            id = id,
            title = title,
            price = price,
            pictures = pictures.map { it.toDomain() },
            freeShipping = shipping.freeShipping,
            condition = condition,
            warranty = warranty,
            acceptsMercadoPago = acceptsMercadoPago,
            attributes = attributes.map { it.toDomain() }
        )
    }

    private fun AttributeResponse.toDomain(): AttributeDomain {
        return AttributeDomain(
            id = id,
            name = name,
            description = description
        )
    }

    private fun PictureResponse.toDomain(): PictureDomain {
        return PictureDomain(
            id = id,
            url = url,
        )
    }
}