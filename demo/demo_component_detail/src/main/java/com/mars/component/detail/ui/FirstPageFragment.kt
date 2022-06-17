package com.mars.component.detail.ui

import androidx.fragment.app.Fragment
import com.rubik.annotations.route.RRoute
import com.rubik.annotations.route.function.RFunction

class FirstPageFragment
@RFunction(
    path = "fragment/page1",
    resultType = Fragment::class
)
constructor() : Fragment() {
    val name = "FirstPageFragment"
}