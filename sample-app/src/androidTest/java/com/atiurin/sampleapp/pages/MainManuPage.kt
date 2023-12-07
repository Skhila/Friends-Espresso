package com.atiurin.sampleapp.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atiurin.sampleapp.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers

object MainManuPage {
    val mainManuBtn: Matcher<View> by lazy { withId(R.id.toolbar) }
    val textViewField: Matcher<View> by lazy { withId(R.id.text2) }
    val manuItem: Matcher<View> by lazy { Matchers.allOf(withId(R.id.design_menu_item_text)) }
}

