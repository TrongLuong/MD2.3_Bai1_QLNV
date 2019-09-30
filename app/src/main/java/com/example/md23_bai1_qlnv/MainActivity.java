package com.example.md23_bai1_qlnv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomAdapter customAdapter;
    ArrayList<NhanVien> arrayListNV;
    private ListView listViewNV;
    private int selectposition;
    private CheckBox checkBoxDele;
    private Button buttonDeleAll, buttonNhapnv;
    private EditText editTextID, editTextTen;
    private RadioGroup radioGroupGender;

    private void anhxa() {
        listViewNV = findViewById(R.id.livNhanVien);
        buttonDeleAll = findViewById(R.id.btnDeleAll);
        buttonNhapnv = findViewById(R.id.btnNhapnv);
        checkBoxDele = findViewById(R.id.cbDeleItem);
        radioGroupGender = findViewById(R.id.rdGenderGroup);
        editTextID = findViewById(R.id.edtManv);
        editTextTen = findViewById(R.id.edtTennv);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        arrayListNV = new ArrayList<>();
        arrayListNV.add(new NhanVien("1", "Trong", true));
        arrayListNV.add(new NhanVien("2", "Trong", false));
        arrayListNV.add(new NhanVien("3", "Trong", true));
        arrayListNV.add(new NhanVien("4", "Trong", false));
        arrayListNV.add(new NhanVien("5", "Trong", false));
        customAdapter = new CustomAdapter(this, R.layout.custom_adapter_liv, arrayListNV);
        listViewNV.setAdapter(customAdapter);
        registerForContextMenu(listViewNV);
        listViewNV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectposition = i;
                return false;
            }
        });
        buttonNhapnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nhapNV();

            }
        });
        buttonDeleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaNV();
                if(!checkBoxDele.isChecked()){
                    Toast.makeText(MainActivity.this, "Chon nhan vien muon xoa!", Toast.LENGTH_SHORT).show();
                }
            }
        });








    }

    public void nhapNV() {
        String i = editTextID.getText().toString();
        String t = editTextTen.getText().toString();
        boolean g = true;
        if (radioGroupGender.getCheckedRadioButtonId() == R.id.rdNu) {
            g = false;
        }
        NhanVien nv = new NhanVien(i, t, g);
        arrayListNV.add(nv);
        customAdapter.notifyDataSetChanged();
        editTextID.setText("");
        editTextTen.setText("");
        editTextID.requestFocus();
    }

    public void xoaNV() {
        for (int i = listViewNV.getChildCount() - 1; i >= 0; i--) {
           View v = listViewNV.getChildAt(i);
           checkBoxDele = v.findViewById(R.id.cbDeleItem);
            if (checkBoxDele.isChecked()) {
                arrayListNV.remove(i);
                Toast.makeText(MainActivity.this, "Xoa thanh cong!", Toast.LENGTH_SHORT).show();

            }

            customAdapter.notifyDataSetChanged();
        }
    }
//menu tren header
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_context,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itXemCT:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
//menu khi nhan su kien onLongClick tren liv
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context,menu);
        menu.setHeaderTitle("Chọn công việc!");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.itXemCT:
                    Intent intent = new Intent(MainActivity.this, ThongTinCT.class);
                    NhanVien nhanVien = arrayListNV.get(selectposition);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("nv",nhanVien);
                    intent.putExtra("objnv",bundle);
                    //mo acti
                    startActivity(intent);

                    break;
                case  R.id.itCapNhat:
                    Intent intent1 = new Intent(MainActivity.this,CapNhat.class);
                    NhanVien nhanVien1 = arrayListNV.get(selectposition);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("nv1",nhanVien1);
                    intent1.putExtra("objnv1",bundle1);
                    //mo acti
                    startActivityForResult(intent1,1000);
                    break;
            }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            Bundle bundle = data.getBundleExtra("objnv1");
            NhanVien nhanVien = (NhanVien) bundle.getSerializable("nv1");
            arrayListNV.get(selectposition).setId(nhanVien.getId());
            arrayListNV.get(selectposition).setName(nhanVien.getName());
            listViewNV.setAdapter(customAdapter);




        }
    }
}
