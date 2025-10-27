/*
 * @ (#) KhachHangDAO         08 Oct 2025
 *
 *Copyright (c) 2025 IUH . All right reserved
 */
package dao;

import connectDB.ConnectDB;
import entities.KhachHang;
import entities.NhanVien;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * @description:  This class represents a bank with many bank accounts
 * @author: Nguyen Van Sy
 * @version: 1.0
 * @created: 08 Oct 2025 13:25
 */
public class KhachHangDAO {
    public KhachHangDAO(){}
    public ArrayList<KhachHang> getAllTbKhachHang() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from KhachHang where trangThai=1";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maKhachHang = rs.getString(1);
                String soDienThoai = rs.getString(2);
                String tenKhachHang = rs.getString(3);
                String hangThanhVien = rs.getString(4);
                boolean gioiTinh = rs.getBoolean(5);
                LocalDate thoiGianTao = rs.getDate(6).toLocalDate();
                String maNhanVien = rs.getString(7);
                boolean trangThai=rs.getBoolean(8);
                NhanVien nv = new NhanVien(maNhanVien);
                KhachHang kh = new KhachHang(maKhachHang, soDienThoai, tenKhachHang, hangThanhVien, gioiTinh, thoiGianTao, nv,trangThai);
                dsKhachHang.add(kh);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsKhachHang;
    }
    public boolean themKhachHang(KhachHang kh)
    {

        String sql="Insert Into KhachHang(maKhachHang,soDienThoai,tenKhachHang,hangThanhVien,gioiTinh,thoiGianTao,maNhanVien) Values(?,?,?,?,?,?,?) ";
        try{
            ConnectDB.getInstance();
            Connection con=ConnectDB.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setString(1,kh.getMaKhachHang());
            stmt.setString(2,kh.getSoDienThoai());
            stmt.setString(3,kh.getTenKhachHang());
            stmt.setString(4,kh.getHangThanhVien());
            stmt.setBoolean(5,kh.isGioiTinh());
            stmt.setDate(6,Date.valueOf(kh.getThoiGianTao()) );
            stmt.setString(7,kh.getMaNhanVien());

            return stmt.executeUpdate()>0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean capNhatKhachHang(KhachHang kh)
    {
        String sql="Update KhachHang Set soDienThoai=?,tenKhachHang=?,hangThanhVien=?,gioiTinh=?,thoiGianTao=?";
        try
        {
            ConnectDB.getInstance();
            Connection con=ConnectDB.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql);

            stmt.setString(1,kh.getSoDienThoai());
            stmt.setString(2,kh.getTenKhachHang());
            stmt.setString(3,kh.getHangThanhVien());
            stmt.setBoolean(4,kh.isGioiTinh());
            stmt.setDate(5  ,Date.valueOf(kh.getThoiGianTao()) );

            return stmt.executeUpdate()>0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean xoaKhachHang(String maKhachHang)
    {
        String sql="Update KhachHang Set trangThai=0 Where maKhachHang=?";
        try{
            ConnectDB.getInstance();
            Connection con=ConnectDB.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setString(1,maKhachHang);
            return stmt.executeUpdate()>0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }



}
