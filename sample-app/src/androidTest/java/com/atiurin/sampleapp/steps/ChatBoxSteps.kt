package com.atiurin.sampleapp.steps

import com.atiurin.sampleapp.pages.ChatPage

object ChatBoxSteps {
    fun checkAddressee(expectedFriendName: String): ChatBoxSteps {
        ChatPage.assertToolbarTitle(expectedFriendName)

        return this
    }

    fun greetAFriend(greet: String): ChatBoxSteps {
        ChatPage.sendMessage(greet)

        return this
    }

    fun checkSentGreeting(expectedGreetText: String): ChatBoxSteps {
        ChatPage.assertMessageDisplayed(expectedGreetText)

        return this
    }
}
