package com.br.products.presentation.productdetail.view.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag

class ProductDetailFragmentRobotAssertions {
    fun assertProductDetailContentIsDisplayed(composeContentTestRule: ComposeContentTestRule) {
        composeContentTestRule.onNodeWithTag("ProductDetailContent", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}