/*
 * @ (#) $NAME.java     1.0     10/15/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package dao;

import connectDB.ConnectDB;
import entities.KeThuoc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/15/2025 1:17 PM
 */
public class KeThuocDAO {
    public KeThuocDAO() {}
    public ArrayList<KeThuoc> getAllTblKeThuoc() {
        ArrayList<KeThuoc> dsKe = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from KeThuoc";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maKe = rs.getString(1);
                String viTri = rs.getString(2);
                String loaiKe = rs.getString(3);
                int sucChua = rs.getInt(4);
                KeThuoc ke = new KeThuoc(maKe, viTri, loaiKe, sucChua);
                dsKe.add(ke);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKe;
    }
}
