package com.eakurnikov.instrsample.matchers

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class BackgroundColorMatcher(
    @ColorRes
    private val colorRes: Int = -1,
    private val colorCode: String? = null
) : TypeSafeMatcher<View>() {

    companion object {
        fun withBackgroundColor(@ColorRes colorRes: Int) = BackgroundColorMatcher(colorRes)
    }

    override fun matchesSafely(item: View?): Boolean {
        return when {
            colorRes == -1 && colorCode.isNullOrEmpty() -> {
                item?.background == null
            }
            item == null -> {
                false
            }
            else -> {
                val expectedColor: Int = if (colorRes != -1) {
                    ContextCompat.getColor(item.context, colorRes)
                } else {
                    Color.parseColor(colorCode)
                }

                (item.background?.current as? ColorDrawable)?.color == expectedColor
            }
        }
    }

    override fun describeTo(description: Description) {
        description.appendText("with background color: $colorRes or $colorCode")
    }
}
