/*
 * @ (#) $NAME.java     1.0     10/12/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package entities;

import java.util.Objects;

/*
 * @description:
 * @author: Khang, Le Hoang
 * @version: 1.0
 * @created: 10/12/2025 9:45 PM
 */
public class NhomThuoc {
    private String maNhom;
    private String tenNhom;
    private Thue thue;

    public NhomThuoc(String maNhom, String tenNhom, Thue thue) {
        this.maNhom = maNhom;
        this.tenNhom = tenNhom;
        this.thue = thue;
    }
    public NhomThuoc(){}
    public NhomThuoc(String maNhom) {
        this.maNhom = maNhom;
    }

    public Thue getThue() {
        return thue;
    }

    public void setThue(Thue thue) {
        this.thue = thue;
    }

    public String getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(String maNhom) {
        this.maNhom = maNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    @Override
    public String toString() {
        return "NhomThuoc{" +
                "maNhom='" + maNhom + '\'' +
                ", tenNhom='" + tenNhom + '\'' +
                ", thue=" + thue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhomThuoc nhomThuoc = (NhomThuoc) o;
        return Objects.equals(maNhom, nhomThuoc.maNhom);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNhom);
    }
}
