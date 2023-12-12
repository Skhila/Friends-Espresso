package com.atiurin.sampleapp.tests.espresso

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.atiurin.sampleapp.activity.MainActivity
import com.atiurin.sampleapp.data.Constants
import com.atiurin.sampleapp.steps.ChatBoxSteps
import com.atiurin.sampleapp.steps.MainMenuSteps
import com.atiurin.sampleapp.steps.MainPageSteps
import com.atiurin.sampleapp.steps.UiElementsPageSteps
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChattingTests {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun emmetBrownChatTest() {
        with(MainPageSteps) {
            scrollToFriendChatByName(Constants.friendNameEmmet)
            openFriendChatByName(Constants.friendNameEmmet)
        }

        with(ChatBoxSteps) {
            checkAddressee(Constants.friendNameEmmet)
            greetAFriend(Constants.emmetMessage)
            checkSentGreeting(Constants.emmetMessage)
        }
    }

    @Test
    fun friend17ChatTest() {
        with(MainPageSteps) {
            scrollToFriendChatByName(Constants.friendNameFriend17)
            openFriendChatByName(Constants.friendNameFriend17)
        }

        with(ChatBoxSteps) {
            checkAddressee(Constants.friendNameFriend17)
            greetAFriend(Constants.friend17Message)
            checkSentGreeting(Constants.friend17Message)
        }
    }

    @Test
    fun uiElementsTest() {
        with(MainPageSteps) {
            checkIfDashboardIsLoaded()
            openMainMenu()
        }

        with(MainMenuSteps) {
            chooseMenuItem(Constants.uiElementsMenuItemName)
        }

        with(UiElementsPageSteps) {
            checkUiElementsPage()
            unmarkEnableCheckBox()
            unmarkClickableCheckBox()
            validateEnableCheckboxUnmarkedStatus()
            validateClickableCheckboxUnmarkedStatus()
            chooseInvisibleOption()
            validateSimpleButtonVisibility()
        }
    }

//    @Test
//    fun greetFriendTest() {
//        MainPageSteps
//            .checkIfDashboardIsLoaded()
//            .openFriendChatByName(Constants.friendName)
//
//        ChatBoxSteps
//            .checkAddressee(Constants.friendName)
//            .greetAFriend(Constants.friendGreeting)
//            .checkSentGreeting(Constants.friendGreeting)
//    }
//
//    @Test
//    fun customClicksTest() {
//        MainPageSteps
//            .checkIfDashboardIsLoaded()
//            .openMainMenu()
//
//        MainMenuSteps.chooseMenuItem(Constants.customClicksItemName)
//
//        CustomClicksPageSteps.checkCustomClickPage().markAllCornerCircles().validateCornerCircles()
//    }
}
