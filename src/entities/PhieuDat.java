package entities;

import java.time.LocalDate;

public class PhieuDat {
    private String maPhieuDat;
    private LocalDate thoiGianDat;
    private String trangThai;
    private String ghiChu;
    private KhachHang khachHang;
    private NhanVien nhanVien;

    public PhieuDat(String maPhieuDat, LocalDate thoiGianDat, String trangThai, String ghiChu, KhachHang khachHang, NhanVien nhanVien) {
        this.maPhieuDat = maPhieuDat;
        this.thoiGianDat = thoiGianDat;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
    }

    public PhieuDat() {
    }
    public PhieuDat(PhieuDat pt) {
        this.maPhieuDat = pt.maPhieuDat;
        this.thoiGianDat = pt.thoiGianDat;
        this.trangThai = pt.trangThai;
        this.ghiChu = pt.ghiChu;
        this.khachHang = pt.khachHang;
        this.nhanVien = pt.nhanVien;
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public LocalDate getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(LocalDate thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "PhieuDat{" +
                "maPhieuDat='" + maPhieuDat + '\'' +
                ", thoiGianDat=" + thoiGianDat +
                ", trangThai='" + trangThai + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
