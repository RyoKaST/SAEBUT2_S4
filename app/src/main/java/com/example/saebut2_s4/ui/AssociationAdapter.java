package com.example.saebut2_s4.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.model.Association;
import java.util.List;

public class AssociationAdapter extends BaseAdapter {
    private Context context;
    private List<Association> associations;

    public AssociationAdapter(Context context, List<Association> associations) {
        this.context = context;
        this.associations = associations;
    }

    @Override
    public int getCount() {
        return associations.size();
    }

    @Override
    public Object getItem(int position) {
        return associations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_association, parent, false);
        }

        TextView name = convertView.findViewById(R.id.association_name);
        TextView description = convertView.findViewById(R.id.association_description);

        Association association = associations.get(position);
        name.setText(association.getNomAssociation());
        description.setText(association.getDescriptionAssociation());

        return convertView;
    }
}
