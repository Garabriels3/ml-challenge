package com.br.products.presentation.productdetail.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.br.products.presentation.fake.FakeMainActivity
import com.br.products.presentation.productdetail.view.robot.ProductDetailFragmentRobotArrangement
import com.br.products.presentation.productdetail.view.robot.ProductDetailFragmentRobotAssertions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailFragmentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<FakeMainActivity>()

    private val productDetailFragmentRobotArrangement = ProductDetailFragmentRobotArrangement()
    private val productDetailFragmentRobotAssertions = ProductDetailFragmentRobotAssertions()

    @Test
    fun whenProductDetailFragmentStart_ShouldShowProductDetail() {
        // Arrange
        productDetailFragmentRobotArrangement.launchFragment(
            composeTestRule, "1"
        )

        // Act

        // Assert
//        productDetailFragmentRobotAssertions.assertProductDetailContentIsDisplayed(composeTestRule)
    }

}