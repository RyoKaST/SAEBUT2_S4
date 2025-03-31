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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.saebut2_s4.data.dao.UtilisateurDao;
import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Utilisateur;

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
        TextView mailInfo = view.findViewById(R.id.mail_info);
        TextView phoneInfo = view.findViewById(R.id.phone_info);
        TextView nameInfo = view.findViewById(R.id.name_info);
        TextView firstNameInfo = view.findViewById(R.id.first_name_info);
        Button logoutButton = view.findViewById(R.id.logout_button);

        boolean isLoggedIn = checkUserLoginStatus();

        if (isLoggedIn) {
            connectedLayout.setVisibility(View.VISIBLE);
            notConnectedLayout.setVisibility(View.GONE);

            // Retrieve user details from SharedPreferences
            String userEmail = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                    .getString("user_email", "Unknown Email");

            // Load user details from the database
            loadUserDetails(userEmail, user -> {
                if (user != null) {
                    accountInfo.setText("Utilisateur : " + user.getNomUtilisateur() + " " + user.getPrenomUtilisateur());
                    mailInfo.setText("E-Mail : " + user.getEmailUtilisateur());
                    phoneInfo.setText("Téléphone : " + user.getTelephoneUtilisateur());
                    nameInfo.setText("Nom : " + user.getNomUtilisateur());
                    firstNameInfo.setText("Prénom : " + user.getPrenomUtilisateur());
                }
            });

            // Handle email modification
            view.findViewById(R.id.mail_change).setOnClickListener(v -> {
                showEditDialog("Modifier l'email", mailInfo.getText().toString(), newEmail -> {
                    if (!newEmail.isEmpty()) {
                        isEmailUnique(newEmail, isUnique -> {
                            if (isUnique) {
                                updateUserDetails(userEmail, "email", newEmail, success -> {
                                    if (success) {
                                        mailInfo.setText("E-Mail : " + newEmail);
                                        saveToSharedPreferences("user_email", newEmail);
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Cet email est déjà utilisé !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            });

            // Handle phone modification
            view.findViewById(R.id.phone_change).setOnClickListener(v -> {
                showEditDialog("Modifier le téléphone", phoneInfo.getText().toString(), newPhone -> {
                    if (!newPhone.isEmpty()) {
                        updateUserDetails(userEmail, "telephone", newPhone, success -> {
                            if (success) {
                                phoneInfo.setText("Téléphone : " + newPhone);
                            }
                        });
                    }
                });
            });

            // Handle name modification
            view.findViewById(R.id.modify_name).setOnClickListener(v -> {
                showEditDialog("Modifier le nom", nameInfo.getText().toString(), newName -> {
                    if (!newName.isEmpty()) {
                        updateUserDetails(userEmail, "nom", newName, success -> {
                            if (success) {
                                nameInfo.setText("Nom : " + newName);
                            }
                        });
                    }
                });
            });

            // Handle first name modification
            view.findViewById(R.id.modify_first_name).setOnClickListener(v -> {
                showEditDialog("Modifier le prénom", firstNameInfo.getText().toString(), newFirstName -> {
                    if (!newFirstName.isEmpty()) {
                        updateUserDetails(userEmail, "prenom", newFirstName, success -> {
                            if (success) {
                                firstNameInfo.setText("Prénom : " + newFirstName);
                            }
                        });
                    }
                });
            });

            // Handle password modification
            view.findViewById(R.id.modify_password).setOnClickListener(v -> {
                showEditDialog("Modifier le mot de passe", "", newPassword -> {
                    if (!newPassword.isEmpty()) {
                        updateUserDetails(userEmail, "password", newPassword, success -> {
                            if (success) {
                                Toast.makeText(getContext(), "Mot de passe mis à jour avec succès", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            });

            // Handle logout button click
            logoutButton.setOnClickListener(v -> {
                requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE).edit()
                        .clear()
                        .apply();

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

    private void loadUserDetails(String email, OnUserLoadedListener listener) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(getContext());
            UtilisateurDao utilisateurDao = appDatabase.utilisateurDao();
            Utilisateur user = utilisateurDao.getUtilisateurByEmail(email);

            requireActivity().runOnUiThread(() -> listener.onUserLoaded(user));
        }).start();
    }

    private void updateUserDetails(String email, String field, String newValue, OnUpdateListener listener) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(getContext());
            UtilisateurDao utilisateurDao = appDatabase.utilisateurDao();
            Utilisateur user = utilisateurDao.getUtilisateurByEmail(email);

            if (user != null) {
                switch (field) {
                    case "email":
                        user.setEmailUtilisateur(newValue);
                        break;
                    case "telephone":
                        user.setTelephoneUtilisateur(newValue);
                        break;
                    case "nom":
                        user.setNomUtilisateur(newValue);
                        break;
                    case "prenom":
                        user.setPrenomUtilisateur(newValue);
                        break;
                    case "password":
                        user.setMotDePasseUtilisateur(newValue);
                        break;
                }
                utilisateurDao.update(user);
                requireActivity().runOnUiThread(() -> listener.onUpdate(true));
            } else {
                requireActivity().runOnUiThread(() -> listener.onUpdate(false));
            }
        }).start();
    }

    private void isEmailUnique(String email, OnEmailCheckListener listener) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(getContext());
            UtilisateurDao utilisateurDao = appDatabase.utilisateurDao();
            boolean isUnique = utilisateurDao.getUtilisateurByEmail(email) == null;

            requireActivity().runOnUiThread(() -> listener.onResult(isUnique));
        }).start();
    }

    // Listener interfaces
    private interface OnUserLoadedListener {
        void onUserLoaded(Utilisateur user);
    }

    private interface OnUpdateListener {
        void onUpdate(boolean success);
    }

    private interface OnEmailCheckListener {
        void onResult(boolean isUnique);
    }

    private void showEditDialog(String title, String currentValue, OnValueChangeListener listener) {
        // Create and show a dialog for editing user information
        EditText input = new EditText(getContext());
        input.setText(currentValue);

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Enregistrer", (dialog, which) -> listener.onValueChanged(input.getText().toString().trim()))
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void saveToSharedPreferences(String key, String value) {
        requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE).edit()
                .putString(key, value)
                .apply();
    }

    private interface OnValueChangeListener {
        void onValueChanged(String newValue);
    }

    private boolean checkUserLoginStatus() {
        // Retrieve login status from SharedPreferences
        return requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE)
                .getBoolean("is_logged_in", false);
    }
}
