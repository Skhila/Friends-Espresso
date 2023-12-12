package com.atiurin.sampleapp.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import com.atiurin.sampleapp.helper.assertIfViewIsNotDisplayed
import com.atiurin.sampleapp.helper.assertIsViewDisplayed
import com.atiurin.sampleapp.pages.UiElementsPage
import com.atiurin.ultron.extensions.assertMatches
import com.atiurin.ultron.extensions.tap

object UiElementsPageSteps {
    fun checkUiElementsPage() {
        with(UiElementsPage) {
            defaultContentDescriptionText.assertIsViewDisplayed()
            invisibleRadioButton.assertIsViewDisplayed()
            clickableCheckBox.assertIsViewDisplayed()
            enableCheckBox.assertIsViewDisplayed()
        }
    }

    fun unmarkEnableCheckBox() {
        UiElementsPage.enableCheckBox.tap()
    }

    fun unmarkClickableCheckBox() {
        UiElementsPage.clickableCheckBox.tap()
    }

    fun validateEnableCheckboxUnmarkedStatus() {
        onView(UiElementsPage.enableCheckBox).assertMatches(isNotChecked())
    }

    fun validateClickableCheckboxUnmarkedStatus() {
        onView(UiElementsPage.clickableCheckBox).assertMatches(isNotChecked())
    }

    fun chooseInvisibleOption() {
        with(UiElementsPage) {
            invisibleRadioButton.tap()
            visibleRadioButton.tap()
            invisibleRadioButton.tap()
        }
    }

    fun validateSimpleButtonVisibility() {
        UiElementsPage.simpleButton.assertIfViewIsNotDisplayed()
    }
}
