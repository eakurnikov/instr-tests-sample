package com.eakurnikov.instrsample.screens

import android.view.View
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.view.FlakyActivity
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object PostsScreen : KScreen<PostsScreen>() {
    override val viewClass: Class<*> = FlakyActivity::class.java
    override val layoutId: Int = R.layout.activity_posts

    val errorTextView = KTextView {
        withId(R.id.tv_posts_error)
    }

    val postsList = KRecyclerView(
        builder = {
            withId(R.id.list_posts)
        },
        itemTypeBuilder = {
            itemType(::PostItem)
        }
    )

    class PostItem(parent: Matcher<View>) : KRecyclerItem<PostItem>(parent) {

        val title = KTextView(parent) {
            withId(R.id.tv_post_title)
        }

        val body = KTextView(parent) {
            withId(R.id.tv_post_body)
        }
    }
}
