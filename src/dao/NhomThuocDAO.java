/*
 * @ (#) $NAME.java     1.0     10/15/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package dao;

import connectDB.ConnectDB;
import entities.NhomThuoc;
import entities.Thue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/15/2025 1:23 PM
 */
public class NhomThuocDAO {
    public NhomThuocDAO() {}
    public ArrayList<NhomThuoc> getAllTblNhomThuoc() {
        ArrayList<NhomThuoc> dsNhom = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * From NhomThuoc";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maNhom = rs.getString(1);
                String tenNhom = rs.getString(2);
                String maThue = rs.getString(3);
                Thue thue = new Thue(maThue);
                NhomThuoc nhom = new NhomThuoc(maNhom, tenNhom, thue);
                dsNhom.add(nhom);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsNhom;
    }
}
