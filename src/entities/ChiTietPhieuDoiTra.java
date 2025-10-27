package entities;

public class ChiTietPhieuDoiTra {
    private PhieuDoiTra phieuDoiTra;
    private int soLuong;
    private LoThuoc loThuoc;


    public ChiTietPhieuDoiTra(PhieuDoiTra phieuDoiTra, int soLuong, LoThuoc loThuoc) {
        this.phieuDoiTra = phieuDoiTra;
        this.soLuong = soLuong;
        this.loThuoc = loThuoc;
    }

    public ChiTietPhieuDoiTra() {
    }
    public ChiTietPhieuDoiTra(ChiTietPhieuDoiTra ctpdt) {
        this.phieuDoiTra = ctpdt.phieuDoiTra;
        this.soLuong = ctpdt.soLuong;
        this.loThuoc = ctpdt.loThuoc;
    }

    public PhieuDoiTra getPhieuDoiTra() {
        return phieuDoiTra;
    }

    public void setPhieuDoiTra(PhieuDoiTra phieuDoiTra) {
        this.phieuDoiTra = phieuDoiTra;
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
        return "ChiTietPhieuDoiTra{" +
                "phieuDoiTra=" + phieuDoiTra +
                ", soLuong=" + soLuong +
                ", loThuoc=" + loThuoc +
                '}';
    }
}
