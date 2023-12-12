package com.atiurin.sampleapp.steps

import com.atiurin.sampleapp.helper.assertIsViewDisplayed
import com.atiurin.sampleapp.pages.ChatPage
import com.atiurin.sampleapp.pages.FriendsListPage
import com.atiurin.sampleapp.pages.UIElementPage
import com.atiurin.ultron.extensions.tap

object MainPageSteps {
    fun checkIfDashboardIsLoaded(): MainPageSteps {
        UIElementPage.dashboardTitle.assertIsViewDisplayed()
        UIElementPage.dashboardContainer.assertIsViewDisplayed()
        return this
    }

    fun openFriendChatByName(name: String) {
        FriendsListPage.openChat(name)
    }

    fun scrollToFriendChatByName(name: String) {
        FriendsListPage.getListItem(name).scrollToItem()
    }

    fun openMainMenu(): MainPageSteps {
        UIElementPage.mainMenuBtn.tap()
        return this
    }
}
