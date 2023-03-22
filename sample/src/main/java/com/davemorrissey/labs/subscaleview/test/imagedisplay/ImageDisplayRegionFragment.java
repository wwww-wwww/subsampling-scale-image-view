package com.davemorrissey.labs.subscaleview.test.imagedisplay;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.test.R.id;
import com.davemorrissey.labs.subscaleview.test.R.layout;

public class ImageDisplayRegionFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.imagedisplay_region_fragment, container, false);
        final SubsamplingScaleImageView imageView = rootView.findViewById(id.imageView);
        imageView.setImage(ImageSource.asset(getContext(), "card.png").region(new Rect(5200, 651, 8200, 3250)));
        final ImageDisplayActivity activity = (ImageDisplayActivity) getActivity();
        if (activity != null) {
            rootView.findViewById(id.previous).setOnClickListener(v -> activity.previous());
        }
        return rootView;
    }
}
