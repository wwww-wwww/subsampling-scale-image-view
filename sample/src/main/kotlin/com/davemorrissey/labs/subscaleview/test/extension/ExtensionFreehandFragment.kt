package com.davemorrissey.labs.subscaleview.test.extension

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ExtensionFreehandFragmentBinding

class ExtensionFreehandFragment : Fragment() {

    private lateinit var binding: ExtensionFreehandFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExtensionFreehandFragmentBinding.inflate(inflater, container, false)

        val activity = activity as? ExtensionActivity
        binding.previous.setOnClickListener { activity?.previous() }
        binding.reset.setOnClickListener { binding.imageView.reset() }

        binding.imageView.setImage(ImageSource.asset(requireContext(), "sanmartino.jpg"))

        return binding.root
    }
}