package com.davemorrissey.labs.subscaleview.test.imagedisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ImagedisplayLargeFragmentBinding

class ImageDisplayLargeFragment : Fragment() {

    private lateinit var binding: ImagedisplayLargeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImagedisplayLargeFragmentBinding.inflate(inflater, container, false)

        val activity = activity as? ImageDisplayActivity
        binding.next.setOnClickListener { activity?.next() }

        binding.imageView.setImage(ImageSource.asset(context, "card.png"))

        return binding.root
    }
}