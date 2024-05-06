package com.br.products.presentation.productdetail.view.robot

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.navigation.Navigation.findNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.br.products.R
import com.br.products.presentation.fake.FakeMainActivity
import kotlinx.coroutines.test.runTest

class ProductDetailFragmentRobotArrangement {

    fun launchFragment(
        composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<FakeMainActivity>, FakeMainActivity>,
        s: String
    ) = runTest {
        composeTestRule.activityRule.scenario.onActivity {
            findNavController(it, R.id.fragment_container)
                .navigate(R.id.productDetailFragment)
        }
    }

    fun withOnResumedState() {

    }
}