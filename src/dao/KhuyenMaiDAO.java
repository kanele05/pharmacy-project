/*
 * @ (#) $NAME.java     1.0     10/15/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package dao;

import connectDB.ConnectDB;
import entities.KhuyenMai;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/15/2025 1:21 AM
 */
public class KhuyenMaiDAO {
    public KhuyenMaiDAO(){}
    public ArrayList<KhuyenMai> getAllTblKhuyenMai() {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from KhuyenMai where TrangThai=1";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maKhuyenMai = rs.getString(1);
                String tenChuongTrinh = rs.getString(2);
                LocalDate tuNgay = rs.getDate(3).toLocalDate();
                LocalDate denNgay = rs.getDate(4).toLocalDate();
                double phanTramGiamGia = rs.getDouble(5);
                int dieuKienApDung = rs.getInt(6);
                KhuyenMai km = new KhuyenMai(maKhuyenMai, tenChuongTrinh, tuNgay, denNgay, phanTramGiamGia, dieuKienApDung);
                dsKM.add(km);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsKM;
    }
    public boolean themKhuyenMai(KhuyenMai km)
    {
        String sql="Insert Into KhuyenMai(maKhuyenMai,tenChuongTrinh,tuNgay,denNgay,phanTramGiamGia,dieuKienApDung) Values(?,?,?,?,?,?)";
      try
      {
          ConnectDB.getInstance();
          Connection con=ConnectDB.getConnection();
          PreparedStatement stmt=con.prepareStatement(sql);
          stmt.setString(1,km.getMaKM());
          stmt.setString(2,km.getTenCT());
          stmt.setDate(3, Date.valueOf(km.getTuNgay()));
          stmt.setDate(4,Date.valueOf(km.getDenNgay()));
          stmt.setInt(5, (int) km.getPhanTramGiamGia());
          stmt.setDouble(6,km.getDieuKienApDung());
          return stmt.executeUpdate()>0;
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
      return false;
    }
    public boolean xoaKhuyenMai(String maKhuyenMai)
    {
        String sql="Update KhuyenMai Set TrangThai=0 Where maKhuyenMai=?";
        try{
            ConnectDB.getInstance();
            Connection con=ConnectDB.getConnection();
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setString(1,maKhuyenMai);
            return stmt.executeUpdate()>0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean capNhatKhuyenMai(KhuyenMai km)
    {
        String sql="Update KhuyenMai Set tenChuongTrinh=?,tuNgay=?,denNgay=?,phanTramGiamGia=?,dieuKienApDung=?";
       try {
           ConnectDB.getInstance();
           Connection con=ConnectDB.getConnection();
           PreparedStatement stmt=con.prepareStatement(sql);
           stmt.setString(1,km.getTenCT());
           stmt.setDate(2, Date.valueOf(km.getTuNgay()));
           stmt.setDate(3,Date.valueOf(km.getDenNgay()));
           stmt.setDouble(4,km.getPhanTramGiamGia());
           stmt.setInt(5,km.getDieuKienApDung());
           return stmt.executeUpdate()>0;

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       return false;
    }
}
