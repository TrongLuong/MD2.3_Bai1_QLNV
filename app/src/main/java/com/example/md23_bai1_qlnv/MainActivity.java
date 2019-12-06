package com.example.md23_bai1_qlnv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataNhanVien dbNhanVien = new DataNhanVien(this);
    CustomAdapter customAdapter;
    List<NhanVien> arrayListNV;


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
//        dbNhanVien.addNhanVien(new NhanVien("1", "Trọng", 1));
//        dbNhanVien.addNhanVien(new NhanVien("2", "Trinh", 0));
//        dbNhanVien.addNhanVien(new NhanVien("3", "Trắng", 0));
//        dbNhanVien.addNhanVien(new NhanVien("4", "Trong", 1));
//        dbNhanVien.addNhanVien(new NhanVien("5", "Trẻ", 0));
        registerForContextMenu(listViewNV);
        arrayListNV = dbNhanVien.getAllNhanVie();
        setCustomAdapter();

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
                arrayListNV.clear();
                //Load lại dữ liệu đã thay đổi
                arrayListNV.addAll(dbNhanVien.getAllNhanVie());

                //hiện lên livView
                setCustomAdapter();


            }
        });
        buttonDeleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaNV();
                arrayListNV.clear();
                //Load lại dữ liệu đã thay đổi
                arrayListNV.addAll(dbNhanVien.getAllNhanVie());
                //hiện lên livView
                setCustomAdapter();

            }
        });

    }

    public void setCustomAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter(this, R.layout.custom_adapter_liv, arrayListNV);
            listViewNV.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            listViewNV.setSelection(customAdapter.getCount());
        }

    }

    public void nhapNV() {
//        String ktraid = editTextID.getText().toString();
//        NhanVien nvkt = dbNhanVien.getNhanVienID(ktraid);
//        if(nvkt != null){
//            if(ktraid.trim().length()==0){
//                Toast.makeText(this, "Mã NV là bắt buộc nhập!",Toast.LENGTH_SHORT).show();
//            }
//             if(nvkt.getId()==editTextID.getText().toString()){
//                Toast.makeText(this, "Trùng MSNV, Nhân viên đã tồn tại!",Toast.LENGTH_SHORT).show();
//            }
//        }
        String idt ="";
        String ktraid = editTextID.getText().toString();
        if(ktraid.trim().length()==0){
                Toast.makeText(this, "Mã NV là bắt buộc nhập!",Toast.LENGTH_SHORT).show();
        }else {
            idt = editTextID.getText().toString();
        }



        String t = editTextTen.getText().toString();
        int gt = 1;//gt = 1 /Nam
        boolean g = true; //Nam
        if (radioGroupGender.getCheckedRadioButtonId() == R.id.rdNu) {
            g = false;//Nu
        }
        if (g == false) {
            gt = 0;//Nu

        } else {
            gt = 1;//Nam
        }
        NhanVien nv = new NhanVien(idt, t, gt);
        dbNhanVien.addNhanVien(nv);
        setCustomAdapter();
        customAdapter.notifyDataSetChanged();
        editTextID.setText("");
        editTextTen.setText("");
        //editTextID.requestFocus();
    }

    public void xoaNV() {
        for (int i = listViewNV.getChildCount() - 1; i >= 0; i--) {
            View v = listViewNV.getChildAt(i);
            checkBoxDele = v.findViewById(R.id.cbDeleItem);
            if (checkBoxDele.isChecked()) {
                String idd = arrayListNV.get(i).getId();
                String ht = arrayListNV.get(i).getName();
                int gtt = arrayListNV.get(i).getGender();
                NhanVien nv = new NhanVien(idd, ht, gtt);
                dbNhanVien.deleteNhanVien(nv);
                arrayListNV.remove(i);
                Toast.makeText(MainActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
            }

            customAdapter.notifyDataSetChanged();
        }
    }

    //menu khi nhan su kien onLongClick tren liv
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Chọn công việc!");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itXemCT:
                Intent intent = new Intent(MainActivity.this, ThongTinCT.class);
                NhanVien nhanVien = arrayListNV.get(selectposition);
                Bundle bundle = new Bundle();
                bundle.putSerializable("nv", nhanVien);
                intent.putExtra("objnv", bundle);
                //mo acti
                startActivity(intent);

                break;
            case R.id.itCapNhat:
                Intent intent1 = new Intent(MainActivity.this, CapNhat.class);
                NhanVien nhanVien1 = arrayListNV.get(selectposition);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("nv1", nhanVien1);
                intent1.putExtra("objnv1", bundle1);
                //mo acti
                startActivityForResult(intent1, 1000);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000) {
            Bundle bundle = data.getBundleExtra("objnv1");
            NhanVien nhanVien = (NhanVien) bundle.getSerializable("nv1");
            arrayListNV.get(selectposition).setId(nhanVien.getId());
            arrayListNV.get(selectposition).setName(nhanVien.getName());
            arrayListNV.get(selectposition).setGender(nhanVien.getGender());
            listViewNV.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();


        }
    }
}
