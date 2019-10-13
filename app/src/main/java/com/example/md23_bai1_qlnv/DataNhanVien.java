package com.example.md23_bai1_qlnv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class DataNhanVien  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "nhanvien_list";
    private static final String TABLE_NAME = "nhanvien";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GT = "gt";

    private Context context;

    public DataNhanVien(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLquery ="create table "+TABLE_NAME+" ("+
                ID + " integer primary key, " +
                NAME + " text, " +
                GT + " integer)";
        sqLiteDatabase.execSQL(SQLquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Toast.makeText(context, "Xoa thanh cong table " + TABLE_NAME, Toast.LENGTH_SHORT).show();

    }
    public void addNhanVien(NhanVien nv){
        SQLiteDatabase db = this.getWritableDatabase();//ghi du lieu vao db
        ContentValues values = new ContentValues();//khoi tao gia tri
        values.put(ID, nv.getId());
        values.put(NAME, nv.getName());
        values.put(GT,nv.getGender());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //delete NhanVien theo  id
    public void deleteNhanVien(NhanVien nv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID +" =?",new String[]{valueOf(nv.getId())});
        db.close();
    }
    //update nhanvien
    public int updateNhanVien(NhanVien nv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, nv.getId());
        values.put(NAME, nv.getName());
        values.put(GT, nv.getGender());
        return db.update(TABLE_NAME, values,ID+ "=?", new String[]{String.valueOf(nv.getId())});
    }
    //search nv by id
    public NhanVien getNhanVienID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,NAME,GT},ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        NhanVien nv = new NhanVien(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
        cursor.close();
        db.close();
        return nv;
    }
    // lay tat ca nhan vien
    public List<NhanVien> getAllNhanVie() {
        List<NhanVien> nhanVienList = new ArrayList<>();

        String query = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                NhanVien nv = new NhanVien();
                nv.setId(cursor.getString(0));
                nv.setName(cursor.getString(1));
                nv.setGender(cursor.getInt(2));
                nhanVienList.add(nv);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  nhanVienList;
    }

}
