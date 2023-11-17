package com.davemorrissey.labs.subscaleview.test.extension

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ExtensionPinFragmentBinding

class ExtensionPinFragment : Fragment() {

    private lateinit var binding: ExtensionPinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExtensionPinFragmentBinding.inflate(inflater, container, false)

        val activity = activity as? ExtensionActivity
        binding.next.setOnClickListener { activity?.next() }

        binding.imageView.setImage(ImageSource.asset(requireContext(), "sanmartino.jpg"))
        binding.imageView.setPin(PointF(1602f, 405f))

        return binding.root
    }
}