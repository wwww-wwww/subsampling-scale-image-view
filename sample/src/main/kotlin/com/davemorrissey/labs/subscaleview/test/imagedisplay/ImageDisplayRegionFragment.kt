package com.davemorrissey.labs.subscaleview.test.imagedisplay

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ImagedisplayRegionFragmentBinding

class ImageDisplayRegionFragment : Fragment() {

    private lateinit var binding: ImagedisplayRegionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImagedisplayRegionFragmentBinding.inflate(inflater, container, false)

        val activity = activity as? ImageDisplayActivity
        binding.previous.setOnClickListener { activity?.previous() }

        binding.imageView.setImage(
            ImageSource.asset(requireContext(), "card.png").region(Rect(5200, 651, 8200, 3250))
        )

        return binding.root
    }
}