package entities;

import java.time.LocalDate;

public class LoThuoc {
    private String maLo;
    private int soLo;
    private LocalDate ngaySX;
    private  LocalDate hanSD;
    private double giaNhap;
    private int soLuongTon;
    private Thuoc thuoc;

    public LoThuoc(String maLo, int soLo, LocalDate ngaySX, LocalDate hanSD, double giaNhap, int soLuongTon, Thuoc thuoc) {
        this.maLo = maLo;
        this.soLo = soLo;
        this.ngaySX = ngaySX;
        this.hanSD = hanSD;
        this.giaNhap = giaNhap;
        this.soLuongTon = soLuongTon;
        this.thuoc = thuoc;
    }

    public LoThuoc() {
    }

    public LoThuoc(String maLo) {
        this.maLo = maLo;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public void setMaLo(String maLo) {
        if(maLo == null){
            throw new IllegalArgumentException("Mã lô bắt buộc và không được rỗng");
        }
        this.maLo = maLo;
    }

    public void setSoLo(int soLo) {
        if (soLo < 0){
            throw new IllegalArgumentException("Số lô > 0");
        }
        this.soLo = soLo;
    }

    public void setNgaySX(LocalDate ngaySX) {
        if (ngaySX == null){
            throw new IllegalArgumentException("Ngày sản xuất không được null");
        }
        if (ngaySX.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ngày sản xuất không thể sau ngày hôm nay");
        }
        this.ngaySX = ngaySX;
    }

    public void setHanSD(LocalDate hanSD) {
        if (hanSD == null){
            throw new IllegalArgumentException("Hạn sử dụng không được bỏ trống");
        }
        if (hanSD.isBefore(ngaySX)){
            throw new IllegalArgumentException("Hạn sử dụng phải sau ngày sản xuất");
        }
        this.hanSD = hanSD;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public void setSoLuongTon(int soLuongTon) {
        if(soLuongTon < 0){
            throw new IllegalArgumentException("Số lượng tồn phải >=0");
        }
        this.soLuongTon = soLuongTon;
    }

    public String getMaLo() {
        return maLo;
    }

    public int getSoLo() {
        return soLo;
    }

    public LocalDate getNgaySX() {
        return ngaySX;
    }

    public LocalDate getHanSD() {
        return hanSD;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    @Override
    public String toString() {
        return "LoThuoc{" +
                "maLo='" + maLo + '\'' +
                ", soLo=" + soLo +
                ", ngaySX=" + ngaySX +
                ", hanSD=" + hanSD +
                ", giaNhap=" + giaNhap +
                ", soLuongTon=" + soLuongTon +
                ", thuoc=" + thuoc +
                '}';
    }
}
