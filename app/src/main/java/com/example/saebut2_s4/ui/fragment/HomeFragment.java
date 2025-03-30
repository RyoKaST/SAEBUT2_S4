package com.example.saebut2_s4.ui.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.ui.EffectuerDonActivity;
import com.example.saebut2_s4.ui.FirstPageDonActivity;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button faire_don = view.findViewById(R.id.faire_don);
        faire_don.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FirstPageDonActivity.class));
            }
        });
    }
}
