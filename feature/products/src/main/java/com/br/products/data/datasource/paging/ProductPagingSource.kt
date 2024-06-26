package com.br.products.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.br.products.data.model.response.ProductItemResponse
import com.br.products.data.service.ProductsService

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
                data = response.productsItem,
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