package entities;

public class ChiTietPhieuNhap {
    private PhieuNhap phieuNhap;
    private double donGiaNhap;
    private int soLuong;
    private LoThuoc loThuoc;


    public ChiTietPhieuNhap(PhieuNhap phieuNhap, double donGiaNhap, int soLuong, LoThuoc loThuoc) {
        this.phieuNhap = phieuNhap;
        this.donGiaNhap = donGiaNhap;
        this.soLuong = soLuong;
        this.loThuoc = loThuoc;
    }

    public ChiTietPhieuNhap() {
    }
    public ChiTietPhieuNhap(ChiTietPhieuNhap ctpn) {
        this.phieuNhap = ctpn.phieuNhap;
        this.donGiaNhap = ctpn.donGiaNhap;
        this.soLuong = ctpn.soLuong;
        this.loThuoc = ctpn.loThuoc;
    }


    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public LoThuoc getLoThuoc() {
        return loThuoc;
    }

    public void setLoThuoc(LoThuoc loThuoc) {
        this.loThuoc = loThuoc;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhap{" +
                "phieuNhap=" + phieuNhap +
                ", donGiaNhap=" + donGiaNhap +
                ", soLuong=" + soLuong +
                ", loThuoc=" + loThuoc +
                '}';
    }
}
