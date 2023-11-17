package com.davemorrissey.labs.subscaleview.test.eventhandling

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.test.AbstractPagesActivity
import com.davemorrissey.labs.subscaleview.test.Page
import com.davemorrissey.labs.subscaleview.test.R.id
import com.davemorrissey.labs.subscaleview.test.R.layout
import com.davemorrissey.labs.subscaleview.test.R.string

class EventHandlingActivity : AbstractPagesActivity(
    string.event_title, layout.pages_activity, listOf(
        Page(string.event_p1_subtitle, string.event_p1_text),
        Page(string.event_p2_subtitle, string.event_p2_text),
        Page(string.event_p3_subtitle, string.event_p3_text)
    )
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageView = findViewById<SubsamplingScaleImageView>(id.imageView)
        imageView.setImage(ImageSource.asset(this, "sanmartino.jpg"))
        imageView.setOnClickListener { v: View ->
            Toast.makeText(
                v.context,
                "Clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        imageView.setOnLongClickListener { v: View ->
            Toast.makeText(v.context, "Long clicked", Toast.LENGTH_SHORT).show()
            true
        }
    }
}