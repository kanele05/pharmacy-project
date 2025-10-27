package entities;

import java.time.LocalDate;

public class HoaDon {
    private String maHoaDon;
    private LocalDate ngayLap;
    private String phuongThucThanhToan;
    private KhachHang khachHang;
    private NhanVien nhanVien;


    public HoaDon(String maHoaDon, LocalDate ngayLap, String phuongThucThanhToan, KhachHang khachHang, NhanVien nhanVien) {
        this.maHoaDon = maHoaDon;
        setMaHoaDon(maHoaDon);
        this.ngayLap = ngayLap;
        setNgayLap(ngayLap);
        this.phuongThucThanhToan = phuongThucThanhToan;
        setPhuongThucThanhToan(phuongThucThanhToan);
        this.khachHang = khachHang;
        setMaKhachHang(khachHang);
        this.nhanVien = nhanVien;
        setMaNhanVien(nhanVien);
    }

    public HoaDon() {
    }

    public HoaDon(HoaDon hd) {
        this.maHoaDon = hd.maHoaDon;
        this.ngayLap = hd.ngayLap;
        this.phuongThucThanhToan = hd.phuongThucThanhToan;
        this.khachHang = hd.khachHang;
        this.nhanVien = hd.nhanVien;

    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public KhachHang getMaKhachHang() {
        return khachHang;
    }

    public NhanVien getMaNhanVien() {
        return nhanVien;
    }

    public void setMaHoaDon(String maHoaDon) {
        if (!maHoaDon.matches("^HD\\d{3}"))
        {
            throw new IllegalArgumentException("Mã Hóa Đơn phải đúng định dạng vd:HD001");
        }
        this.maHoaDon = maHoaDon;
    }

    public void setNgayLap(LocalDate ngayLap) {
        if (ngayLap.isAfter(LocalDate.now()))
        {
            throw new IllegalArgumentException("Không được sau ngày hôm nay");
        }
        this.ngayLap = ngayLap;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        if (phuongThucThanhToan.isEmpty() || phuongThucThanhToan.isBlank()){
            throw new IllegalArgumentException("Phương Thức Thanh Toán không được rỗng");
        }
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public void setMaKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public void setMaNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", ngayLap=" + ngayLap +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
