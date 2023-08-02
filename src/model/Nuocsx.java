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
public class Nuocsx implements Serializable{
    private int ma;
    private String ten;
    private String moTa;
    private String action;

    public Nuocsx() {
    }

    public Nuocsx(int ma, String ten, String moTa) {
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
    }

    public Nuocsx(int ma, String ten, String moTa, String action) {
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
        this.action = action;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public Object[] toObject(){
        return new Object[]{
            ma,ten,moTa
        };
    }
    
}
