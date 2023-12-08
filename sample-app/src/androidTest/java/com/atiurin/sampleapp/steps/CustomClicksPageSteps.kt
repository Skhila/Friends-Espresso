package com.atiurin.sampleapp.steps

import com.atiurin.sampleapp.helper.assertIsViewDisplayed
import com.atiurin.sampleapp.helper.isChecked
import com.atiurin.sampleapp.pages.CustomClicksPage
import com.atiurin.ultron.extensions.tap
import org.junit.Assert

object CustomClicksPageSteps {
    fun checkCustomClickPage(): CustomClicksPageSteps {
        CustomClicksPage.topLeftRB.assertIsViewDisplayed()
        CustomClicksPage.topRightRB.assertIsViewDisplayed()
        CustomClicksPage.bottomLeftRB.assertIsViewDisplayed()
        CustomClicksPage.bottomRightRB.assertIsViewDisplayed()

        return this
    }

    fun markAllCornerCircles(): CustomClicksPageSteps {
        CustomClicksPage.topLeftRB.tap()
        CustomClicksPage.topRightRB.tap()
        CustomClicksPage.bottomLeftRB.tap()
        CustomClicksPage.bottomRightRB.tap()

        return this
    }

    fun validateCornerCircles(): CustomClicksPageSteps {
        val isAllChecked = CustomClicksPage.topLeftRB.isChecked() &&
            CustomClicksPage.topRightRB.isChecked() &&
            CustomClicksPage.bottomLeftRB.isChecked() &&
            CustomClicksPage.bottomRightRB.isChecked()

        Assert.assertTrue(isAllChecked)

        return this
    }
}
