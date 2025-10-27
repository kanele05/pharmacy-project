package entities;

import java.time.LocalDate;

public class KhachHang {
    private String maKhachHang;
    private String soDienThoai;
    private String tenKhachHang;
    private String hangThanhVien;
    private boolean gioiTinh; // true = Nam, false = Nữ
    private LocalDate thoiGianTao;
    private NhanVien nhanVien;
    private boolean trangThai;

    public KhachHang() {}

    public KhachHang(String maKhachHang, String soDienThoai, String tenKhachHang,
                     String hangThanhVien, boolean gioiTinh,
                     LocalDate thoiGianTao, NhanVien nhanVien, boolean trangThai) {
        this.maKhachHang = maKhachHang;
        this.soDienThoai = soDienThoai;
        this.tenKhachHang = tenKhachHang;
        this.hangThanhVien = hangThanhVien;
        this.gioiTinh = gioiTinh;
        this.thoiGianTao = thoiGianTao;
        this.nhanVien = nhanVien;
        this.trangThai = trangThai;
    }

    public KhachHang(String maKhachHang, String soDienThoai, String tenKhachHang,
                     String hangThanhVien, boolean gioiTinh,
                     LocalDate thoiGianTao, NhanVien nhanVien) {
        this(maKhachHang, soDienThoai, tenKhachHang, hangThanhVien, gioiTinh, thoiGianTao, nhanVien, true);
    }

    public KhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        if (!maKhachHang.matches("^KH\\d{3}$")) {
            throw new IllegalArgumentException("Mã Khách Hàng phải đúng định dạng, ví dụ: KH001");
        }
        this.maKhachHang = maKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (!soDienThoai.matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Số điện thoại phải gồm 10 số và bắt đầu bằng 0");
        }
        this.soDienThoai = soDienThoai;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        this.tenKhachHang = tenKhachHang.trim();
    }

    public String getHangThanhVien() {
        return hangThanhVien;
    }

    public void setHangThanhVien(String hangThanhVien) {
        if (hangThanhVien == null || hangThanhVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Hạng thành viên không được để trống");
        }
        this.hangThanhVien = hangThanhVien.trim();
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getGioiTinhString() {
        return gioiTinh ? "Nam" : "Nữ";
    }

    public LocalDate getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDate thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getMaNhanVien() {
        return (nhanVien != null) ? nhanVien.getMaNhanVien() : "";
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKhachHang='" + maKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", hangThanhVien='" + hangThanhVien + '\'' +
                ", gioiTinh=" + getGioiTinhString() +
                ", thoiGianTao=" + thoiGianTao +
                ", nhanVien=" + (nhanVien != null ? nhanVien.getMaNhanVien() : "null") +
                ", trangThai=" + trangThai +
                '}';
    }
}
