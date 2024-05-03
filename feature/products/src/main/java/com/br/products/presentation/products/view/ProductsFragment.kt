package com.br.products.presentation.products.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.view.compose.ProductsScreen
import com.br.products.presentation.products.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModel()
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.handleAction(
                ProductsUiAction.OnStartScreenAction(
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
                        state = viewModel.uiState.collectAsStateWithLifecycle().value,
                        effect = viewModel.uiSideEffect,
                        navController = navController,
                        triggerAction = {
                            viewModel.handleAction(it)
                        }
                    )
                }
            }
        }
    }
}