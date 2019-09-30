package com.example.md23_bai1_qlnv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThongTinCT extends AppCompatActivity {
    TextView txtid, txtname, txtgt;
    Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ct);
        txtid = findViewById(R.id.txtManv);
        txtname = findViewById(R.id.txtTennv);
        txtgt = findViewById(R.id.txtGT);
        buttonClose = findViewById(R.id.btnclose);
        //nhan du lieu tu main
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("objnv");
        NhanVien nhanVien = (NhanVien) bundle.getSerializable("nv");

        txtid.setText(nhanVien.getId());
        txtname.setText(nhanVien.getName());
        if (nhanVien.isGender()) {
            txtgt.setText("Nam");
        } else
            txtgt.setText("Nu");


        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
