package com.atiurin.sampleapp.steps

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atiurin.sampleapp.R
import com.atiurin.ultron.extensions.tap
import org.hamcrest.Matcher
import org.hamcrest.Matchers

object MainMenuSteps {
    fun chooseMenuItem(itemName: String): MainMenuSteps {
        val menuItem: Matcher<View> by lazy { Matchers.allOf(withId(R.id.design_menu_item_text), withText(itemName)) }

        menuItem.tap()
        return this
    }
}
