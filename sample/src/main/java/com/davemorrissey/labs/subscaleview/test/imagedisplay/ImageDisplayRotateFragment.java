package com.davemorrissey.labs.subscaleview.test.imagedisplay;

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

public class ImageDisplayRotateFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.imagedisplay_rotate_fragment, container, false);
        final SubsamplingScaleImageView imageView = rootView.findViewById(id.imageView);
        imageView.setImage(ImageSource.asset(getContext(), "swissroad.jpg"));
        final ImageDisplayActivity activity = (ImageDisplayActivity) getActivity();
        if (activity != null) {
            rootView.findViewById(id.previous).setOnClickListener(v -> activity.previous());
            rootView.findViewById(id.next).setOnClickListener(v -> activity.next());
        }
        return rootView;
    }
}
