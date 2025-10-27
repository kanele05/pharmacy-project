/*
 * @ (#) NhanVienDAO         08 Oct 2025
 *
 *Copyright (c) 2025 IUH . All right reserved
 */
package dao;

import connectDB.ConnectDB;
import entities.ChucVu;
import entities.NhanVien;
import entities.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * @description:  This class represents a bank with many bank accounts
 * @author: Nguyen Van Sy
 * @version: 1.0
 * @created: 08 Oct 2025 14:36
 */
public class NhanVienDAO {
    public NhanVienDAO() {
    }
    public ArrayList<NhanVien> getAllTblNhanVien() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT nv.maNhanVien, nv.tenNhanVien, nv.soDienThoai, nv.diaChi, nv.anhDaiDien,\n" +
                    "       cv.maChucVu, cv.tenChucVu,\n" +
                    "       tk.maTaiKhoan\n" +
                    "FROM NhanVien nv\n" +
                    "JOIN ChucVu  cv ON nv.maChucVu = cv.maChucVu\n" +
                    "JOIN TaiKhoan tk ON nv.maTaiKhoan = tk.maTaiKhoan";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maNhanVien = rs.getString("maNhanVien");
                String tenNhanVien = rs.getString("tenNhanVien");
                String soDienThoai = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                String anhDaiDien = rs.getString("anhDaiDien");
                String maChucVu = rs.getString("maChucVu");
                String tenChucVu = rs.getString("tenChucVu");
                String maTaiKhoan = rs.getString("maTaiKhoan");
                ChucVu cv = new ChucVu(maChucVu, tenChucVu);
                TaiKhoan tk = new TaiKhoan(maTaiKhoan);
                NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, soDienThoai, diaChi, anhDaiDien, cv, tk);
                dsNhanVien.add(nv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsNhanVien;
    }
}
