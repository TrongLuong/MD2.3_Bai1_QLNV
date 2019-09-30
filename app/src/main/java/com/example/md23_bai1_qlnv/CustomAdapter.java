package com.example.md23_bai1_qlnv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<NhanVien> arrayListNV;
    NhanVien nv;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<NhanVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListNV = (ArrayList<NhanVien>) objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(R.layout.custom_adapter_liv, parent, false);
        ImageView imageIcon = convertView.findViewById(R.id.imageIconItem);
        TextView textViewID = convertView.findViewById(R.id.txtManvItem);
        TextView textViewTen = convertView.findViewById(R.id.txtTennvItem);
//        RadioButton radioButtonNam = convertView.findViewById(R.id.rdNam);
//        RadioButton radioButtonNu = convertView.findViewById(R.id.rdNu);
        CheckBox checkBoxDele = convertView.findViewById(R.id.cbDeleItem);

        nv = arrayListNV.get(position);
        if(nv.isGender()){
            imageIcon.setImageResource(R.drawable.personel);
        }else {
            imageIcon.setImageResource(R.drawable.woman);
        }
        textViewID.setText(nv.getId());
        textViewTen.setText(nv.getName());

        return convertView;
    }
}
