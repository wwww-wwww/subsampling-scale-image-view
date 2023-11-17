package com.davemorrissey.labs.subscaleview.test.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.test.databinding.ViewPagerPageBinding

class ViewPagerFragment : Fragment() {

    private lateinit var binding: ViewPagerPageBinding

    var asset: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPagerPageBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                asset = savedInstanceState.getString(BUNDLE_ASSET)
            }
        }
        asset?.let {
            binding.imageView.setImage(ImageSource.asset(requireContext(), it))
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (view != null) {
            outState.putString(BUNDLE_ASSET, asset)
        }
    }
}

private const val BUNDLE_ASSET = "asset"
