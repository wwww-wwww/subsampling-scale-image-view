package com.davemorrissey.labs.subscaleview.test.imagedisplay

import android.util.Log
import com.davemorrissey.labs.subscaleview.test.AbstractFragmentsActivity
import com.davemorrissey.labs.subscaleview.test.Page
import com.davemorrissey.labs.subscaleview.test.R.id
import com.davemorrissey.labs.subscaleview.test.R.layout
import com.davemorrissey.labs.subscaleview.test.R.string

class ImageDisplayActivity : AbstractFragmentsActivity(
    string.display_title, layout.fragments_activity, listOf(
        Page(string.display_p1_subtitle, string.display_p1_text),
        Page(string.display_p2_subtitle, string.display_p2_text),
        Page(string.display_p3_subtitle, string.display_p3_text)
    )
) {
    override fun onPageChanged(page: Int) {
        try {
            supportFragmentManager
                .beginTransaction()
                .replace(id.frame, fragments[page].getDeclaredConstructor().newInstance())
                .commit()
        } catch (e: Exception) {
            Log.e(ImageDisplayActivity::class.java.name, "Failed to load fragment", e)
        }
    }
}

private val fragments = listOf(
    ImageDisplayLargeFragment::class.java,
    ImageDisplayRotateFragment::class.java,
    ImageDisplayRegionFragment::class.java
)