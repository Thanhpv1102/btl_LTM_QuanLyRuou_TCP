/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author phamv
 */
public class Ruou implements Serializable{
    private int ma;
    private String ten;
    private double nongDo;
    private int soNam;
    private String nuocSanXuat;
    private String hinhAnh;
    private String action;
    private String nongdoSearch, namsxSearch;
    
    public Ruou() {
    }

    public Ruou(int ma, String ten, double nongDo, int soNam, String nuocSanXuat, String hinhAnh) {
        this.ma = ma;
        this.ten = ten;
        this.nongDo = nongDo;
        this.soNam = soNam;
        this.nuocSanXuat = nuocSanXuat;
        this.hinhAnh = hinhAnh;
    }

    public String getNongdoSearch() {
        return nongdoSearch;
    }

    public void setNongdoSearch(String nongdoSearch) {
        this.nongdoSearch = nongdoSearch;
    }
    
    
    public String getNamsxSearch() {
        return namsxSearch;
    }

    public void setNamsxSearch(String namsxSearch) {
        this.namsxSearch = namsxSearch;
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getNongDo() {
        return nongDo;
    }

    public void setNongDo(double nongDo) {
        this.nongDo = nongDo;
    }

    public int getSoNam() {
        return soNam;
    }

    public void setSoNam(int soNam) {
        this.soNam = soNam;
    }

    public String getNuocSanXuat() {
        return nuocSanXuat;
    }

    public void setNuocSanXuat(String nuocSanXuat) {
        this.nuocSanXuat = nuocSanXuat;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    
    public Object[] toObject(){
        return new Object[]{
            ma,ten,nongDo,soNam,nuocSanXuat,hinhAnh
        };
    }
}
