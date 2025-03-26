package com.example.saebut2_s4.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.dao.UtilisateurDao;
import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> utilisateurAffichageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur_list);

        listView = findViewById(R.id.list_view_utilisateurs);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, utilisateurAffichageList);
        listView.setAdapter(adapter);

        // Charger les utilisateurs depuis la base de données
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            UtilisateurDao utilisateurDao = db.utilisateurDao();
            List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs();

            for (Utilisateur u : utilisateurs) {
                utilisateurAffichageList.add(u.getPrenomUtilisateur() + " " + u.getNomUtilisateur());
            }

            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }
}
