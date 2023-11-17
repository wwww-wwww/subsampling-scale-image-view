package com.davemorrissey.labs.subscaleview.test.extension

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ExtensionCircleFragmentBinding

class ExtensionCircleFragment : Fragment() {

    private lateinit var binding: ExtensionCircleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExtensionCircleFragmentBinding.inflate(inflater, container, false)

        val activity = activity as? ExtensionActivity
        binding.previous.setOnClickListener { activity?.previous() }
        binding.next.setOnClickListener { activity?.next() }

        binding.imageView.setImage(ImageSource.asset(requireContext(), "sanmartino.jpg"))

        return binding.root
    }
}