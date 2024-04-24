package com.br.products.domain.model

data class ProductDomain(
    val paging: PagingDomain,
    val productsItem: List<ProductItemDomain>
)

data class PagingDomain(
    val total: Int
)