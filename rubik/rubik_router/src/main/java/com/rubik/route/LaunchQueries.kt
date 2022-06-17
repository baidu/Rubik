package com.rubik.route

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

/**
 * The Queries for rubik to launch a activity.
 *
 * @since 1.0
 */
class LaunchQueries : Queries() {
    var context: Context? = null
    var flags: Int? = null
    var requestCode: Int? = null
    var activity: Activity? = null
    var fragment: Fragment? = null
}