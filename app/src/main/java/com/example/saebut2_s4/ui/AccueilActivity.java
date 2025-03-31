package com.example.saebut2_s4.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.saebut2_s4.ui.fragment.FavoritesFragment;
import com.example.saebut2_s4.ui.fragment.HomeFragment;
import com.example.saebut2_s4.ui.fragment.ProfileFragment;
import com.example.saebut2_s4.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.example.saebut2_s4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acceuil);

        // Use WindowInsetsControllerCompat for managing system UI visibility
        WindowInsetsControllerCompat windowInsetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());

        // Set the behavior of the system bars to be "transparent"
        windowInsetsController.setAppearanceLightNavigationBars(false); // Or true, depending on your design
        windowInsetsController.setAppearanceLightStatusBars(false); // Or true, depending on your design

        // Hide the system bars behind the content
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // Set the layout to draw behind the system bars
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNav = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        // Initialize fragments
        if (savedInstanceState == null) {
            initializeFragments();
        } else {
            // Restore active fragment after configuration change
            activeFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        }

        setupNavigation();
    }

    private void initializeFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Set HomeFragment as the default and only add it initially
        activeFragment = new HomeFragment();
        transaction.add(R.id.fragment_container, activeFragment, "home");

        transaction.commit();
    }

    private void setupNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation); // Assuming you have a BottomNavigationView with this ID in your layout
        bottomNav.setOnItemSelectedListener(this::handleNavigationItemSelected);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            switchFragment(HomeFragment.class, "home");
            return true;
        } else if (itemId == R.id.nav_search) {
            switchFragment(SearchFragment.class, "search");
            return true;
        } else if (itemId == R.id.nav_favorite) {
            switchFragment(FavoritesFragment.class, "favorites");
            return true;
        } else if (itemId == R.id.nav_profile) {
            switchFragment(ProfileFragment.class, "profile");
            return true;
        } else {
            return false;
        }
    }

    private void switchFragment(Class<? extends Fragment> fragmentClass, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Set animations
        transaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
        );

        // Hide current active fragment
        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }

        // Find existing fragment or create a new one lazily
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                transaction.add(R.id.fragment_container, fragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        // Show the new fragment
        transaction.show(fragment);
        activeFragment = fragment;

        transaction.commit();
    }
}
