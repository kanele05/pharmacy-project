package entities;

public class ChiTietPhieuDat {
    private String maChiTietPhieuDat;
    private int soLuong;
    private double donGiaDat;
    private PhieuDat phieuDat;
    private Thuoc thuoc;

    public ChiTietPhieuDat(String maChiTietPhieuDat, int soLuong, double donGiaDat, PhieuDat phieuDat, Thuoc thuoc) {
        this.maChiTietPhieuDat = maChiTietPhieuDat;
        this.soLuong = soLuong;
        this.donGiaDat = donGiaDat;
        this.phieuDat = phieuDat;
        this.thuoc = thuoc;
    }

    public ChiTietPhieuDat() {
    }
    public ChiTietPhieuDat(ChiTietPhieuDat ctpd) {
        this.maChiTietPhieuDat = ctpd.maChiTietPhieuDat;
        this.soLuong = ctpd.soLuong;
        this.donGiaDat = ctpd.donGiaDat;
        this.phieuDat = ctpd.phieuDat;
        this.thuoc = ctpd.thuoc;
    }

    public String getMaChiTietPhieuDat() {
        return maChiTietPhieuDat;
    }

    public void setMaChiTietPhieuDat(String maChiTietPhieuDat) {
        this.maChiTietPhieuDat = maChiTietPhieuDat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaDat() {
        return donGiaDat;
    }

    public void setDonGiaDat(double donGiaDat) {
        this.donGiaDat = donGiaDat;
    }

    public PhieuDat getPhieuDat() {
        return phieuDat;
    }

    public void setPhieuDat(PhieuDat phieuDat) {
        this.phieuDat = phieuDat;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDat{" +
                "maChiTietPhieuDat='" + maChiTietPhieuDat + '\'' +
                ", soLuong=" + soLuong +
                ", donGiaDat=" + donGiaDat +
                ", phieuDat=" + phieuDat +
                ", thuoc=" + thuoc +
                '}';
    }
}
