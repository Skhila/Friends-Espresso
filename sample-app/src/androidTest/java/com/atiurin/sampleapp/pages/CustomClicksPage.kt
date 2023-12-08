package com.atiurin.sampleapp.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.atiurin.sampleapp.R
import org.hamcrest.Matcher

object CustomClicksPage {
    val topLeftRB: Matcher<View> by lazy { withId(R.id.rB_top_left) }
    val topRightRB: Matcher<View> by lazy { withId(R.id.rB_top_right) }
    val bottomLeftRB: Matcher<View> by lazy { withId(R.id.rB_bottom_left) }
    val bottomRightRB: Matcher<View> by lazy { withId(R.id.rB_bottom_right) }
}
