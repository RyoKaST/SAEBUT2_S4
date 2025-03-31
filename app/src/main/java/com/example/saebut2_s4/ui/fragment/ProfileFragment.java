package com.example.saebut2_s4.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.ui.ConnexionActivity;
import com.example.saebut2_s4.ui.InscriptionActivity;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout connectedLayout = view.findViewById(R.id.connected_layout);
        LinearLayout notConnectedLayout = view.findViewById(R.id.not_connected_layout);
        TextView accountInfo = view.findViewById(R.id.account_info);
        Button logoutButton = view.findViewById(R.id.logout_button);

        boolean isLoggedIn = checkUserLoginStatus();

        if (isLoggedIn) {
            connectedLayout.setVisibility(View.VISIBLE);
            notConnectedLayout.setVisibility(View.GONE);

            // Retrieve user details from SharedPreferences
            String userName = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                    .getString("user_name", "Unknown User");
            accountInfo.setText("Utilisateur: " + userName);

            // Handle logout button click
            logoutButton.setOnClickListener(v -> {
                // Clear SharedPreferences
                requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE).edit()
                        .clear()
                        .apply();

                // Redirect to ConnexionActivity
                Intent intent = new Intent(getActivity(), ConnexionActivity.class);
                startActivity(intent);
                requireActivity().finish();
            });
        } else {
            connectedLayout.setVisibility(View.GONE);
            notConnectedLayout.setVisibility(View.VISIBLE);

            Button loginButton = view.findViewById(R.id.login_button);
            Button signupButton = view.findViewById(R.id.signup_button);

            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ConnexionActivity.class);
                startActivity(intent);
            });

            signupButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), InscriptionActivity.class);
                startActivity(intent);
            });
        }
    }

    private boolean checkUserLoginStatus() {
        // Retrieve login status from SharedPreferences
        return requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                .getBoolean("is_logged_in", false);
    }
}
