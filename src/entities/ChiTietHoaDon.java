package entities;

public class ChiTietHoaDon {
    private String maChiTietHoaDon;
    private int soLuong;
    private String maHoaDon;
    private String maThuoc;

    public ChiTietHoaDon(String maChiTietHoaDon, int soLuong, String maHoaDon, String maThuoc) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.soLuong = soLuong;
        this.maHoaDon = maHoaDon;
        this.maThuoc = maThuoc;
    }

    public ChiTietHoaDon() {
    }
    public ChiTietHoaDon(ChiTietHoaDon ct) {
        this.maChiTietHoaDon = ct.maChiTietHoaDon;
        this.soLuong = ct.soLuong;
        this.maHoaDon = ct.maHoaDon;
        this.maThuoc = ct.maThuoc;
    }

    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        if (maChiTietHoaDon.isEmpty()){
            throw new IllegalArgumentException("Mã Chi Tiết Hóa Đơn không được rỗng");
        }
        if (maChiTietHoaDon.matches("^CTHD\\d{3}")){
            throw new IllegalArgumentException("Mã Chi Tiết Hóa Đơn sai định dạng");
        }
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0 ){
            throw new IllegalArgumentException("Số lượng phải >= 0");
        }
        this.soLuong = soLuong;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        if (!maHoaDon.matches("^HD\\d{3}"))
        {
            throw new IllegalArgumentException("Mã Hóa Đơn phải đúng định dạng vd:HD001");
        }
        this.maHoaDon = maHoaDon;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        if(!maThuoc.matches("^TH\\d{3}")){
            throw new IllegalArgumentException("Mã nhân viên không hợp lệ! (Đúng dạng NVxxx, ví dụ NV001)");
        }

        this.maThuoc = maThuoc;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maChiTietHoaDon='" + maChiTietHoaDon + '\'' +
                ", soLuong=" + soLuong +
                ", maHoaDon='" + maHoaDon + '\'' +
                ", maThuoc='" + maThuoc + '\'' +
                '}';
    }
}
