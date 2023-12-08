package com.atiurin.sampleapp.tests.espresso

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.atiurin.sampleapp.Data.Constants
import com.atiurin.sampleapp.activity.MainActivity
import com.atiurin.sampleapp.steps.ChatBoxSteps
import com.atiurin.sampleapp.steps.CustomClicksPageSteps
import com.atiurin.sampleapp.steps.MainMenuSteps
import com.atiurin.sampleapp.steps.MainPageSteps
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChattingTests {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

//    @Test
//    fun textWithMyFriend() {
//        with(UIElementPage) {
//            isTextOnScreen("Friends")
//            nameChandler.isViewDisplayed()
//            nameChandler.tap()
//            textInput.typeText("Hello Rachel")
//
//            messageInputText.isViewDisplayed()
//        }
//    }

    @Test
    fun greetFriendTest() {
        MainPageSteps
            .checkIfDashboardIsLoaded()
            .openFriendChatByName(Constants.friendName)

        ChatBoxSteps
            .checkAddressee(Constants.friendName)
            .greetAFriend(Constants.friendGreeting)
            .checkSentGreeting(Constants.friendGreeting)
    }

    @Test
    fun customClicksTest() {
        MainPageSteps
            .checkIfDashboardIsLoaded()
            .openMainMenu()

        MainMenuSteps.chooseMenuItem(Constants.customClicksItemName)

        CustomClicksPageSteps.checkCustomClickPage().markAllCornerCircles().validateCornerCircles()
    }
}
