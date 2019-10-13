package com.example.md23_bai1_qlnv;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String id;
    private String name;
    private int gender;

    public NhanVien(String id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public NhanVien() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
