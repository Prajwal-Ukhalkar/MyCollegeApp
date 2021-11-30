package com.example.collegeapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.collegeapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Text;



public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private LottieAnimationView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // IMP - In fragments,we have to find View first and then by using view,we can find findViewById..

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        map = view.findViewById(R.id.map);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        sliderLayout.setScrollTimeInSec(2);
        setSliderView();
        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0,0?q=Government Polytechnic, Aurangabad,Maharashtra 43005");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setSliderView() {

        for (int i = 0; i<5; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            switch (i){
                case 0:
                    sliderView.setImageDrawable(R.drawable.gpacampus );
                    break;

                    case 1:
                    sliderView.setImageDrawable(R.drawable.d1 );
                    break;

                    case 2:
                    sliderView.setImageDrawable(R.drawable.d3 );
                    break;

                case 3:
                    sliderView.setImageDrawable(R.drawable.clg1 );
                    break;

                case 4:
                    sliderView.setImageDrawable(R.drawable.clg3);
                    break;

                case 5:
                    sliderView.setImageDrawable(R.drawable.clg4 );
                    break;


            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);

            sliderLayout.addSliderView(sliderView);
        }
    }
}