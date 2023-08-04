/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Nuocsx;
import model.Ruou;

/**
 *
 * @author phamv
 */
public class ServerDAO {

    private Connection conn;
    public static final String URL = "jdbc:mysql://localhost:3306/ltm_quanlyruou_data";
    public static final String USER = "root";
    public static final String PASS = "123456";

    public ServerDAO() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addNuocsx(Nuocsx n) {
        String sql = "INSERT INTO tblnuocsx(ma, ten, mota) VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, n.getMa());
            ps.setString(2, n.getTen());
            ps.setString(3, n.getMoTa());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRuou(Ruou r) {
        String sql = "INSERT INTO tblruou(ma, ten, nongdo, sonam, nuocsx, hinhanh) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getMa());
            ps.setString(2, r.getTen());
            ps.setDouble(3, r.getNongDo());
            ps.setInt(4, r.getSoNam());
            ps.setString(5, r.getNuocSanXuat());
            ps.setString(6, r.getHinhAnh());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Nuocsx> getAllNuocsx() {
        ArrayList<Nuocsx> listNSX = new ArrayList<>();
        String sql = "SELECT * FROM tblnuocsx";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ma = rs.getInt("ma");
                String ten = rs.getString("ten");
                String mota = rs.getString("mota");

                Nuocsx n = new Nuocsx(ma, ten, mota);
                listNSX.add(n);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listNSX;
    }

    public ArrayList<Ruou> getAllRuou() {
        ArrayList<Ruou> listR = new ArrayList<>();
        String sql = "SELECT * FROM tblruou";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ma = rs.getInt("ma");
                String ten = rs.getString("ten");
                double nongdo = rs.getDouble("nongdo");
                int sonam = rs.getInt("sonam");
                String nuocsx = rs.getString("nuocsx");
                String hinhanh = rs.getString("hinhanh");

                Ruou r = new Ruou(ma, ten, nongdo, sonam, nuocsx, hinhanh);
                listR.add(r);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listR;
    }

    public boolean updateNuocsx(Nuocsx n) {
        String sql = "UPDATE tblnuocsx SET ten = ?, mota = ? WHERE ma = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, n.getTen());
            ps.setString(2, n.getMoTa());
            ps.setInt(3, n.getMa());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRuou(Ruou r) {
        String sql = "UPDATE tblruou SET ten = ?, nongdo = ?, sonam = ?, nuocsx = ?, hinhanh = ? WHERE ma = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, r.getTen());
            ps.setDouble(2, r.getNongDo());
            ps.setInt(3, r.getSoNam());
            ps.setString(4, r.getNuocSanXuat());
            ps.setString(5, r.getHinhAnh());
            ps.setInt(6, r.getMa());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNuocsx(Nuocsx n) {
        String sql = "DELETE FROM tblnuocsx WHERE ma = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, n.getMa());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRuou(Ruou r) {
        String sql = "DELETE FROM tblruou WHERE ma = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getMa());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Nuocsx> searchNuocsxByName(Nuocsx n) {
        ArrayList<Nuocsx> listByName = new ArrayList<>();
        String keyword = n.getTen();
        String sql = "SELECT * FROM tblnuocsx WHERE ten LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ma = rs.getInt("ma");
                String ten = rs.getString("ten");
                String mota = rs.getString("mota");

                Nuocsx nn = new Nuocsx(ma, ten, mota);
                listByName.add(nn);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listByName;
    }

    public ArrayList<Ruou> searchRuou(Ruou r) {
        ArrayList<Ruou> resultList = new ArrayList<>();
        String nongDo = r.getNongdoSearch();
        String namSanXuat = r.getNamsxSearch();
        String nuocSanXuat = r.getNuocSanXuat();
        String sql = "SELECT * FROM tblruou";
        StringBuilder whereClause = new StringBuilder();

        boolean hasCondition = false;
        if (!nongDo.equals("all")) {
            whereClause.append(" nongdo ");
            if (nongDo.equals("duoi 5%")) {
                whereClause.append("< 5");
            } else if (nongDo.equals("tu 5% den 15%")) {
                whereClause.append(">= 5 AND nongdo <= 15");
            } else if (nongDo.equals("tren 15%")) {
                whereClause.append("> 15");
            }
            hasCondition = true;
        }

        if (!namSanXuat.equals("all")) {
            if (hasCondition) {
                whereClause.append(" AND");
            }
            whereClause.append(" sonam ");
            if (namSanXuat.equals("truoc nam 2000")) {
                whereClause.append(" < 2000");
            } else if (namSanXuat.equals("tu nam 2000 den 2015")) {
                whereClause.append(" >= 2000 AND sonam <= 2015");
            } else if (namSanXuat.equals("tu 2015 den nay")) {
                whereClause.append(" > 2015");
            }
            hasCondition = true;
        }

        if (!nuocSanXuat.equals("all")) {
            if (hasCondition) {
                whereClause.append(" AND");
            }
            whereClause.append(" nuocsx LIKE '%").append(nuocSanXuat).append("%'");
            hasCondition = true;
        }

        if (hasCondition) {
            sql += " WHERE" + whereClause;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ma = rs.getInt("ma");
                String ten = rs.getString("ten");
                double nongdo = rs.getDouble("nongdo");
                int sonam = rs.getInt("sonam");
                String nuocsx = rs.getString("nuocsx");
                String hinhanh = rs.getString("hinhanh");

                Ruou ruou = new Ruou(ma, ten, nongdo, sonam, nuocsx, hinhanh);
                resultList.add(ruou);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public ArrayList<String> getItemNSX(Ruou r) {
        ArrayList<String> listItemNSX = new ArrayList<>();
        String sql = "SELECT DISTINCT ten FROM tblnuocsx";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nuocSanXuat = rs.getString("ten");
                listItemNSX.add(nuocSanXuat);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listItemNSX;
    }
}
