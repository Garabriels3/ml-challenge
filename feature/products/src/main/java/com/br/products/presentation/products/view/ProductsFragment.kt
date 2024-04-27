package com.br.products.presentation.products.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.view.compose.ProductsScreen
import com.br.products.presentation.products.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.handleAction(
                ProductsUiAction.OnStartScreen(
                    arguments?.getString("searchedTerm") ?: ""
                )
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                MlChallengeTheme {
                    ProductsScreen(
                        state = viewModel.uiState.collectAsState().value,
                        triggerAction = { }
                    )
                }
            }
        }
    }
}