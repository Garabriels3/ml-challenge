package com.br.products.presentation.productdetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.products.presentation.productdetail.udf.ProductDetailUiAction
import com.br.products.presentation.productdetail.view.compose.ProductDetailScreen
import com.br.products.presentation.productdetail.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.handleAction(
                ProductDetailUiAction.OnStartScreenAction(
                    arguments?.getString("productId") ?: ""
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                ProductDetailScreen(
                    state = viewModel.uiState.collectAsState().value,
                    triggerAction = {
                        viewModel.handleAction(it)
                    }
                )
            }
        }
    }
}