package com.example.md23_bai1_qlnv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CapNhat extends AppCompatActivity {
    EditText id, ten;
    RadioGroup radioGroupcn;
    Button buttonLuu;
    RadioButton radioButtonNam, radioButtonNu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat);
        id =findViewById(R.id.edtManvcn);
        ten = findViewById(R.id.edtTennvcn);
        radioGroupcn = findViewById(R.id.rdGenderGroupcn);
        buttonLuu = findViewById(R.id.btnluu);
        radioButtonNam = findViewById(R.id.rdNamcn);
        radioButtonNu = findViewById(R.id.rdNucn);


        //lay datda tu main
        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("objnv1");
        NhanVien nhanVien1 = (NhanVien) bundle.getSerializable("nv1");

        id.setText(nhanVien1.getId());
        ten.setText(nhanVien1.getName());
        if (nhanVien1.getGender()==1){
            radioButtonNam.isChecked();
        }else {
            radioButtonNu.isChecked();
        }
        buttonLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean g = true;
                if (radioGroupcn.getCheckedRadioButtonId() == R.id.rdNucn) {
                    g = false;
                }
                int gt =1;
                if(g ==true){
                    gt = 1;
                }else {
                    gt =0;
                }
                //gui du lieu ve main
                Bundle bundle1 = new Bundle();
                Intent intent1 = new Intent();
                NhanVien nhanVien1 = new NhanVien(id.getText().toString(),ten.getText().toString(),gt);
                bundle1.putSerializable("nv1",nhanVien1);
                intent1.putExtra("objnv1",bundle1);
                setResult(1000,intent1);
                finish();
            }
        });
    }


}
