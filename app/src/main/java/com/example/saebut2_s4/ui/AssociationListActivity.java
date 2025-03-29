package com.example.saebut2_s4.ui;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.saebut2_s4.MyApp;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.model.Association;
import java.util.List;

public class AssociationListActivity extends AppCompatActivity {

    private ListView listView;
    private AssociationAdapter adapter;
    private List<Association> associations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_list);

        listView = findViewById(R.id.list_associations);
        associations = MyApp.getInstance().getAssociations();

        adapter = new AssociationAdapter(this, associations);
        listView.setAdapter(adapter);
    }

}
