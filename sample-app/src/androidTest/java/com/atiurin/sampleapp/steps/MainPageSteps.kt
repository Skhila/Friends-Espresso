package com.atiurin.sampleapp.steps

import com.atiurin.sampleapp.helper.assertIsViewDisplayed
import com.atiurin.sampleapp.pages.FriendsListPage
import com.atiurin.sampleapp.pages.UIElementPage
import com.atiurin.ultron.extensions.tap

object MainPageSteps {
    fun checkIfDashboardIsLoaded(): MainPageSteps {
        UIElementPage.dashboardTitle.assertIsViewDisplayed()
        UIElementPage.dashboardContainer.assertIsViewDisplayed()
        return this
    }

    fun openFriendChatByName(name: String): MainPageSteps {
        FriendsListPage.openChat(name)
        return this
    }

    fun openMainMenu(): MainPageSteps {
        UIElementPage.mainMenuBtn.tap()
        return this
    }
}
