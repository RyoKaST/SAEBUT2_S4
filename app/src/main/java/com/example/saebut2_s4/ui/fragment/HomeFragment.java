package com.example.saebut2_s4.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.dao.DonDao;
import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Don;

import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout donationListLayout = view.findViewById(R.id.donation_list_layout);

        // Check if the user is logged in
        boolean isLoggedIn = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                .getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            // Retrieve the logged-in user's ID
            long userId = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                    .getLong("user_id", -1);

            // Load and display the user's donations
            loadUserDonations(userId, donationListLayout);
        } else {
            // If the user is not logged in, show a message
            TextView notLoggedInMessage = new TextView(getContext());
            notLoggedInMessage.setText("Veuillez vous connecter pour voir vos dons.");
            notLoggedInMessage.setTextColor(getResources().getColor(R.color.white));
            donationListLayout.addView(notLoggedInMessage);
        }
    }

    private void loadUserDonations(long userId, LinearLayout donationListLayout) {
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        DonDao donDao = appDatabase.donDao();

        // Observe the LiveData returned by DonDao
        donDao.recupererDonsParUtilisateur(userId).observe(getViewLifecycleOwner(), donations -> {
            donationListLayout.removeAllViews();

            if (donations == null || donations.isEmpty()) {
                TextView noDonationsMessage = new TextView(getContext());
                noDonationsMessage.setText("Vous n'avez effectué aucun don.");
                noDonationsMessage.setTextColor(getResources().getColor(R.color.white));
                donationListLayout.addView(noDonationsMessage);
            } else {
                for (Don donation : donations) {
                    TextView donationView = new TextView(getContext());
                    donationView.setText("Don de " + donation.getMontantDon() + "€ à l'association ID: " + donation.getAssociationIdDon());
                    donationView.setTextColor(getResources().getColor(R.color.white));
                    donationListLayout.addView(donationView);
                }
            }
        });
    }
}
