/*
 * @ (#) $NAME.java     1.0     10/14/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package entities;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/14/2025 11:29 PM
 */
public class ChucVu {
    private String maChucVu;
    private String tenChucVu;

    public ChucVu(String maChucVu, String tenChucVu) {
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
    }
    public ChucVu(){}
    public ChucVu(String maChucVu){
        this.maChucVu = maChucVu;
    }

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    @Override
    public String toString() {
        return "ChucVu{" +
                "maChucVu='" + maChucVu + '\'' +
                ", tenChucVu='" + tenChucVu + '\'' +
                '}';
    }
}
