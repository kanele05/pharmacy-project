package entities;

import java.time.LocalDate;

public class PhieuNhap {
    private String maPhieuNhap;
    private LocalDate ngayNhap;
    private NhaCungCap nhaCungCap;
    private NhanVien nhanVien;

    public PhieuNhap(String maPhieuNhap, LocalDate ngayNhap, NhaCungCap nhaCungCap, NhanVien nhanVien) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = nhaCungCap;
        this.nhanVien = nhanVien;
    }

    public PhieuNhap() {
    }
    public PhieuNhap(PhieuNhap pn) {
        this.maPhieuNhap = pn.maPhieuNhap;
        this.ngayNhap = pn.ngayNhap;
        this.nhaCungCap = pn.nhaCungCap;
        this.nhanVien = pn.nhanVien;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "PhieuNhap{" +
                "maPhieuNhap='" + maPhieuNhap + '\'' +
                ", ngayNhap=" + ngayNhap +
                ", nhaCungCap=" + nhaCungCap +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
