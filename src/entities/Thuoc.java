package entities;

public class Thuoc {
    private String maThuoc;
    private String tenThuoc;
    private String hamLuong;
    private String dangThuoc;
    private double giaThuoc;
    private String donViTinh;
    private String nhaSanXuat;
    private String trangThai;
    private String anhDaiDien;
    private KeThuoc keThuoc;
    private NhanVien nhanVien;
    private NhomThuoc nhomThuoc;

    public Thuoc(String maThuoc, String tenThuoc, String hamLuong, String dangThuoc, double giaThuoc, String donViTinh, String nhaSanXuat, String trangThai, String anhDaiDien, KeThuoc keThuoc, NhanVien nhanVien, NhomThuoc nhomThuoc) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.hamLuong = hamLuong;
        this.dangThuoc = dangThuoc;
        this.giaThuoc = giaThuoc;
        this.donViTinh = donViTinh;
        this.nhaSanXuat = nhaSanXuat;
        this.trangThai = trangThai;
        this.anhDaiDien = anhDaiDien;
        this.keThuoc = keThuoc;
        this.nhanVien = nhanVien;
        this.nhomThuoc = nhomThuoc;
    }

    public Thuoc(){}

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(String hamLuong) {
        this.hamLuong = hamLuong;
    }

    public String getDangThuoc() {
        return dangThuoc;
    }

    public void setDangThuoc(String dangThuoc) {
        this.dangThuoc = dangThuoc;
    }

    public double getGiaThuoc() {
        return giaThuoc;
    }

    public void setGiaThuoc(double giaThuoc) {
        this.giaThuoc = giaThuoc;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public KeThuoc getKeThuoc() {
        return keThuoc;
    }

    public void setKeThuoc(KeThuoc keThuoc) {
        this.keThuoc = keThuoc;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhomThuoc getNhomThuoc() {
        return nhomThuoc;
    }

    public void setNhomThuoc(NhomThuoc nhomThuoc) {
        this.nhomThuoc = nhomThuoc;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", hamLuong='" + hamLuong + '\'' +
                ", dangThuoc='" + dangThuoc + '\'' +
                ", giaThuoc=" + giaThuoc +
                ", donViTinh='" + donViTinh + '\'' +
                ", nhaSanXuat='" + nhaSanXuat + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", anhDaiDien='" + anhDaiDien + '\'' +
                ", keThuoc=" + keThuoc +
                ", nhanVien=" + nhanVien +
                ", nhomThuoc=" + nhomThuoc +
                '}';
    }
}
