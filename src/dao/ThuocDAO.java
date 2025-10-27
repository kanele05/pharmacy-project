/*
 * @ (#) $NAME.java     1.0     10/12/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package dao;

import connectDB.ConnectDB;
import entities.*;

import java.sql.*;
import java.util.ArrayList;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/12/2025 9:35 PM
 */
public class ThuocDAO {
    public ThuocDAO(){}
    public ArrayList<Thuoc> getAllTblThuoc() {
        ArrayList<Thuoc> dsThuoc = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Thuoc where trangThai = N'Đang Kinh Doanh'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maThuoc = rs.getString(1);
                String tenThuoc = rs.getString(2);
                String hamLuong = rs.getString(3);
                String dangThuoc = rs.getString(4);
                double giaThuoc = rs.getDouble(5);
                String donViTinh = rs.getString(6);
                String nhaSanXuat = rs.getString(7);
                String trangThai = rs.getString(8);
                String anhDaiDien = rs.getString(9);
                String maKe = rs.getString(10);
                String maNhanVien = rs.getString(11);
                String maNhom = rs.getString(12);
                KeThuoc keThuoc = new KeThuoc(maKe);
                NhanVien nv = new NhanVien(maNhanVien);
                NhomThuoc nhomThuoc = new NhomThuoc(maNhom);
                Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, hamLuong, dangThuoc, giaThuoc, donViTinh, nhaSanXuat, trangThai, anhDaiDien, keThuoc, nv, nhomThuoc);
                dsThuoc.add(thuoc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsThuoc;
    }
    public boolean themThuoc(Thuoc thuoc) {
        String sql = "INSERT INTO Thuoc (maThuoc, tenThuoc, hamLuong, dangThuoc, giaThuoc, donViTinh, nhaSanXuat, anhDaiDien, maKe, maNhanVien, maNhom) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, thuoc.getMaThuoc());
            stmt.setString(2, thuoc.getTenThuoc());
            stmt.setString(3, thuoc.getHamLuong());
            stmt.setString(4, thuoc.getDangThuoc());
            stmt.setDouble(5, thuoc.getGiaThuoc());
            stmt.setString(6, thuoc.getDonViTinh());
            stmt.setString(7, thuoc.getNhaSanXuat());
            stmt.setString(8, thuoc.getAnhDaiDien());
            stmt.setString(9, thuoc.getKeThuoc().getMaKe());
            stmt.setString(10, thuoc.getNhanVien().getMaNhanVien());
            stmt.setString(11, thuoc.getNhomThuoc().getMaNhom());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean capNhatThuoc(Thuoc thuoc) {
        String sql = "Update Thuoc set tenThuoc = ?, hamLuong = ?, dangThuoc = ?, giaThuoc = ?, donViTinh = ?, nhaSanXuat = ?, anhDaiDien = ?, maKe = ?, maNhanVien = ?, maNhom = ? where maThuoc = ?";
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, thuoc.getTenThuoc());
            stmt.setString(2, thuoc.getHamLuong());
            stmt.setString(3, thuoc.getDangThuoc());
            stmt.setDouble(4, thuoc.getGiaThuoc());
            stmt.setString(5, thuoc.getDonViTinh());
            stmt.setString(6, thuoc.getNhaSanXuat());
            stmt.setString(7, thuoc.getAnhDaiDien());
            stmt.setString(8, thuoc.getKeThuoc().getMaKe());
            stmt.setString(9, thuoc.getNhanVien().getMaNhanVien());
            stmt.setString(10, thuoc.getNhomThuoc().getMaNhom());
            stmt.setString(11, thuoc.getMaThuoc());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean xoaThuoc (String maThuoc) throws SQLException {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql = "Update Thuoc set trangThai = N'Ngừng Kinh Doanh' where maThuoc = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, maThuoc);
        return stmt.executeUpdate() > 0;
    }
    public Thuoc findById(String maThuoc) {
    String sql = "SELECT maThuoc, tenThuoc, hamLuong, dangThuoc, giaThuoc, donViTinh, nhaSanXuat, trangThai, anhDaiDien, maKe, maNhanVien, maNhom " +
                 "FROM Thuoc WHERE maThuoc = ?";
    try {
        Connection con = ConnectDB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, maThuoc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String ma = rs.getString(1);
            String ten = rs.getString(2);
            String ham = rs.getString(3);
            String dang = rs.getString(4);
            double gia = rs.getDouble(5);
            String dvt = rs.getString(6);
            String nsx = rs.getString(7);
            String trangThai = rs.getString(8);
            String anh = rs.getString(9);
            String maKe = rs.getString(10);
            String maNV = rs.getString(11);
            String maNhom = rs.getString(12);

            KeThuoc ke = new KeThuoc(maKe);
            NhanVien nv = new NhanVien(maNV);
            NhomThuoc nhom = new NhomThuoc(maNhom);

            return new Thuoc(ma, ten, ham, dang, gia, dvt, nsx, trangThai, anh, ke, nv, nhom);
        }
        return null;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}
