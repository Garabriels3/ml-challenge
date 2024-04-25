package com.br.products.presentation.searchproduct.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.presentation.searchproduct.view.compose.SearchProductScreen
import com.br.products.presentation.searchproduct.viewmodel.SearchProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchProductFragment : Fragment() {

    private val viewModel: SearchProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    SearchProductScreen(viewModel.uiState.collectAsState().value)
                }
            }
        }
    }
}