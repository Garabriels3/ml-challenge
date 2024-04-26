package com.br.products.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.products.data.service.ProductsService
import com.br.products.data.model.response.ProductItemResponse

class ProductPagingSource(
    private val service: ProductsService,
    private val query: String
) : PagingSource<Int, ProductItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItemResponse> {
        return try {
            val nextPage = params.key ?: 0
            val response =
                service.getProducts(query = query, limit = params.loadSize, offset = nextPage)

            LoadResult.Page(
                data = response.productsItem.map { it.copy(total = response.paging.total) },
                prevKey = if (nextPage == 0) null else nextPage - params.loadSize,
                nextKey = if (response.productsItem.isEmpty()) null else nextPage + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductItemResponse>): Int? {
        return state.anchorPosition
    }
}